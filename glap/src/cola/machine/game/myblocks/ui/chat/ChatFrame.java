package cola.machine.game.myblocks.ui.chat;

/**
 * Created by colamachine on 16-6-24.
 */

import cola.machine.game.myblocks.registry.CoreRegistry;
import de.matthiasmann.twl.*;
import de.matthiasmann.twl.textarea.HTMLTextAreaModel;
import org.lwjgl.Sys;

public class ChatFrame extends ResizableFrame {
    private final StringBuilder sb;
    private final HTMLTextAreaModel textAreaModel;
    private final TextArea textArea;
    private final EditField editField;
    private final ScrollPane scrollPane;
    private int curColor;

    public ChatFrame() {
        setTitle("Chat");

        this.sb = new StringBuilder();
        this.textAreaModel = new HTMLTextAreaModel();
        this.textArea = new TextArea(textAreaModel);
        this.editField = new EditField();
        CoreRegistry.put(ChatFrame.class , this);
        editField.addCallback(new EditField.Callback() {
            public void callback(int key) {//调用顺序 gui的 handlekey
                System.out.println(key);
                if(key == Event.KEY_RETURN) {
                    // cycle through 3 different colors/font styles
                    appendRow("color"+curColor, editField.getText());
                    editField.setText("");
                    curColor = (curColor + 1) % 3;
                    editField.giveupKeyboardFocus();
                  //  hide();
                }
            }
        });
        //scrollpane < textArea < textAreaModel
        textArea.addCallback(new TextArea.Callback() {
            public void handleLinkClicked(String href) {
                Sys.openURL(href);
            }
        });

        scrollPane = new ScrollPane(textArea);
        scrollPane.setFixed(ScrollPane.Fixed.HORIZONTAL);

        DialogLayout l = new DialogLayout();//一种布局
        l.setTheme("content");
        l.setHorizontalGroup(l.createParallelGroup(scrollPane, editField));//意思是editField放入scrollpane中
        l.setVerticalGroup(l.createSequentialGroup(scrollPane, editField));

        add(l);

        appendRow("default", "Welcome to the chat demo. Type your messages below :)");
    }
    public void setFocus(){
        this.editField.requestKeyboardFocus();
    }

    private void appendRow(String font, String text) {
        sb.append("<div style=\"word-wrap: break-word; font-family: ").append(font).append("; \">");
        // not efficient but simple
        for(int i=0,l=text.length() ; i<l ; i++) {
            char ch = text.charAt(i);
            switch(ch) {
                case '<': sb.append("&lt;"); break;
                case '>': sb.append("&gt;"); break;
                case '&': sb.append("&amp;"); break;
                case '"': sb.append("&quot;"); break;
                case ':':
                    if(text.startsWith(":)", i)) {
                        sb.append("<img src=\"smiley\" alt=\":)\"/>");
                        i += 1; // skip one less because of i++ in the for loop
                        break;
                    }
                    sb.append(ch);
                    break;
                case 'h':
                    if(text.startsWith("http://", i)) {
                        int end = i + 7;
                        while(end < l && isURLChar(text.charAt(end))) {
                            end++;
                        }
                        String href = text.substring(i, end);
                        sb.append("<a style=\"font: link\" href=\"").append(href)
                                .append("\" >").append(href)
                                .append("</a>");
                        i = end - 1; // skip one less because of i++ in the for loop
                        break;
                    }
                    // fall through:
                default:
                    sb.append(ch);
            }
        }
        sb.append("</div>");

        boolean isAtEnd = scrollPane.getMaxScrollPosY() == scrollPane.getScrollPositionY();

        textAreaModel.setHtml(sb.toString());

        if(isAtEnd) {
            scrollPane.validateLayout();
            scrollPane.setScrollPositionY(scrollPane.getMaxScrollPosY());
        }
    }
//public void hide(){
  //  this.fadeToHide(5);

    //this.fadeTo(Color.,1000);
    //this.setTheme(null);
//}
    private boolean isURLChar(char ch) {
        return (ch == '.') || (ch == '/') || (ch == '%') ||
                (ch >= '0' && ch <= '9') ||
                (ch >= 'a' && ch <= 'z') ||
                (ch >= 'A' && ch <= 'Z');
    }
}
