package cola.machine.game.myblocks.engine.paths;


import com.google.common.collect.ImmutableList;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class PathManager {
    public static final String TERASOLOGY_FOLDER_NAME = "myblock";
    public static final Path LINUX_HOME_SUBPATH =null; //Paths.get(".local", "share", "terasology");

    private static final String SAVED_GAMES_DIR = "saves";
    private static final String LOG_DIR = "logs";
    private static final String SHADER_LOG_DIR = "shaders";
    private static final String MOD_DIR = "modules";
    private static final String SCREENSHOT_DIR = "screenshots";
    private static final String NATIVES_DIR = "natives";

    private static PathManager instance;
    private Path installPath;
    private Path homePath;
    private Path savesPath;
    private Path logPath;
    private Path shaderLogPath;
    private Path currentWorldPath;

    private ImmutableList<Path> modPaths = ImmutableList.of();
    private Path screenshotPath;
    private Path nativesPath;

    private PathManager() {
        // By default, the path should be the code location (where terasology.jar is)
        try {
            URL urlToSource = PathManager.class.getProtectionDomain().getCodeSource().getLocation();

            Path codeLocation = Paths.get(urlToSource.toURI());
            System.out.println("codeLocation: " + codeLocation);
            if (Files.isRegularFile(codeLocation)) {
                installPath = findNativesHome(codeLocation.getParent(), 5);
                if (installPath == null) {
                    System.out.println("Failed to find the natives dir - unable to launch!");
                    throw new RuntimeException("Failed to find natives from .jar launch");
                }
            }
        } catch (URISyntaxException e) {
            // Can't use logger, because logger not set up when PathManager is used.
            System.out.println("Failed to convert code location to uri");
        }
        // We might be running from an IDE which can cause the installPath to be null. Try current working directory.
        if (installPath == null) {
            installPath = Paths.get("").toAbsolutePath();
           /* if(installPath.resolve("glap").toFile().exists())
                installPath=installPath.resolve("glap");*/
            System.out.println("installPath was null, running from IDE or headless server? Setting to: " + installPath);
            installPath = findNativesHome(installPath, 5);
            if (installPath == null) {
                System.out.println("Failed to find the natives dir - unable to launch!");
                throw new RuntimeException("Failed to find natives from likely IDE launch");
            }
            nativesPath=installPath.resolve("natives");
        }
        homePath = installPath;
    }

    /**
     * Searches for a parent directory containing the natives directory
     *
     * @param startPath path to start from
     * @param maxDepth  max directory levels to search
     * @return the adjusted path containing the natives directory or null if not found
     */
    private Path findNativesHome(Path startPath, int maxDepth) {
        int levelsToSearch = maxDepth;
        Path checkedPath = startPath;
        while (levelsToSearch > 0) {
            File dirToTest = new File(checkedPath.toFile(), NATIVES_DIR);
            if (dirToTest.exists()) {
                System.out.println("Found the natives dir: " + dirToTest);
                return checkedPath;
            }

            checkedPath = checkedPath.getParent();
            if (checkedPath.equals(startPath.getRoot())) {
                System.out.println("Uh oh, reached the root path, giving up");
                return null;
            }
            levelsToSearch--;
        }

        System.out.println("Failed to find the natives dir within " + maxDepth + " levels of " + startPath);
        return null;
    }

    public static PathManager getInstance() {
        if (instance == null) {
            instance = new PathManager();
        }
        return instance;
    }

    public void useOverrideHomePath(Path rootPath) throws IOException {
        this.homePath = rootPath;
        updateDirs();
    }

    public void useDefaultHomePath() throws IOException {

        //这段暂时注释掉 本来的目的是判断 用户的游戏存档放哪里的 因为 windows 的可能要放在用户目录下
        //先暂时无脑的存放在 游戏文件夹下
       /* switch (LWJGLUtil.getPlatform()) {
            case LWJGLUtil.PLATFORM_LINUX:
                homePath = Paths.get(System.getProperty("user.home")).resolve(LINUX_HOME_SUBPATH);
                break;
            case LWJGLUtil.PLATFORM_MACOSX:
                homePath = Paths.get(System.getProperty("user.home"), "Library", "Application Support", TERASOLOGY_FOLDER_NAME);
                break;
            case LWJGLUtil.PLATFORM_WINDOWS:
               
                Path rawPath;
                rawPath =this.installPath.resolve("saves");
              
                homePath = rawPath.resolve(TERASOLOGY_FOLDER_NAME);
                break;
            default:
                homePath = Paths.get(System.getProperty("user.home")).resolve(LINUX_HOME_SUBPATH);
                break;
        }*/
        updateDirs();
    }

    public Path getCurrentSavePath() {
        return currentWorldPath;
    }

    public void setCurrentSaveTitle(String worldTitle) throws IOException {
        currentWorldPath = getSavePath(worldTitle);
        Files.createDirectories(currentWorldPath);
    }

    public Path getHomePath() {
        return homePath;
    }

    public Path getInstallPath() {
        return installPath;
    }

    public Path getSavesPath() {
        return savesPath;
    }

    public Path getLogPath() {
        return logPath;
    }

    public Path getShaderLogPath() {
        return shaderLogPath;
    }

    public List<Path> getModulePaths() {
        return modPaths;
    }

    public Path getScreenshotPath() {
        return screenshotPath;
    }

    public Path getNativesPath() {
        return nativesPath;
    }

    private void updateDirs() throws IOException {
        Files.createDirectories(homePath);
        savesPath = homePath.resolve(SAVED_GAMES_DIR);
        Files.createDirectories(savesPath);
        logPath = homePath.resolve(LOG_DIR);
        Files.createDirectories(logPath);
        shaderLogPath = logPath.resolve(SHADER_LOG_DIR);
        Files.createDirectories(shaderLogPath);
        Path homeModPath = homePath.resolve(MOD_DIR);
        Files.createDirectories(homeModPath);
        Path installModPath = installPath.resolve(MOD_DIR);
        Files.createDirectories(installModPath);
        if (Files.isSameFile(homeModPath, installModPath)) {
            modPaths = ImmutableList.of(homeModPath);
        } else {
            modPaths = ImmutableList.of(installModPath, homeModPath);
        }
        screenshotPath = homePath.resolve(SCREENSHOT_DIR);
        Files.createDirectories(screenshotPath);
        nativesPath = installPath.resolve(NATIVES_DIR);
        if (currentWorldPath == null) {
            currentWorldPath = homePath;
        }
    }

    public Path getHomeModPath() {
        return modPaths.get(0);
    }

    public Path getSavePath(String title) {
        return savesPath.resolve(title.replaceAll("[^A-Za-z0-9-_ ]", ""));
    }


}
