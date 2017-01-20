package cola.machine.game.myblocks.model.ui.html;

import com.dozenx.game.opengl.util.ShaderUtils;
import com.sun.glass.events.KeyEvent;
import de.matthiasmann.twl.*;
import de.matthiasmann.twl.model.DefaultEditFieldModel;
import de.matthiasmann.twl.model.EditFieldModel;
import de.matthiasmann.twl.renderer.*;
import de.matthiasmann.twl.utils.TextUtil;

import javax.vecmath.Vector4f;

/**
 * Created by luying on 17/1/12.
 */
public class EditField extends TextField {

    //onMouseMove  鼠标变成竖杠
    //onMouseMoveOut 鼠标变成正常的
    //onkeyDown
    //onclick
    //onblur
    //onfocus
    int scrollPos;
    int selectionStart;
    int selectionEnd;
    int numberOfLines;
   public  boolean multiLine;
    boolean pendingScrollToCursor;
    boolean pendingScrollToCursorForce;
    private int maxTextLength = Short.MAX_VALUE;

    private int columns = 5;
    private char passwordChar;
    private Object errorMsg;
    private boolean errorMsgFromModel;
    private boolean textLongerThenWidget;
    private boolean forwardUnhandledKeysToCallback;
    private boolean autoCompletionOnSetText = true;
    private int cursorPos;
    public void setText(String text) {
        setText(text, false);
    }
    public EditField(){
        this.canAcceptKeyboardFocus=true;
        addActionMapping("cut", "cutToClipboard");
        addActionMapping("copy", "copyToClipboard");
        addActionMapping("paste", "pasteFromClipboard");
        addActionMapping("selectAll", "selectAll");
        addActionMapping("duplicateLineDown", "duplicateLineDown");

        KeyStroke[] keys =  new KeyStroke[]{KeyStroke.parse("ctrl X","cut"),
                KeyStroke.parse("ctrl C","copy"),
                KeyStroke.parse("ctrl V","paste"),
                KeyStroke.parse("ctrl A","selectAll")};
        InputMap inputMap =new InputMap(keys);
        this.setInputMap(inputMap);
        //(int modifier, int keyCode, char keyChar, String action) {
    }

    void setText(String text, boolean fromModel) {
        text = TextUtil.limitStringLength(text, maxTextLength);
        editBuffer.replace(0, editBuffer.length(), text);

        cursorPos =  text.length();
        selectionStart = 0;
        selectionEnd = 0;
        updateSelection();
        updateText(autoCompletionOnSetText, fromModel, Event.KEY_NONE);
       scrollToCursor(true);
    }

    public void updateSelection(){
        /*
        if(attributes != null) {
            attributes.removeAnimationState(TextWidget.STATE_TEXT_SELECTION);
            attributes.setAnimationState(TextWidget.STATE_TEXT_SELECTION,
                    selectionStart, selectionEnd, true);
            attributes.optimize();
            textRenderer.cacheDirty = true;
        }*/
    }

    protected int getCursorPosFromMouse(int x, int y) {//根据坐标得到在文本的哪个位置

        //Font font = getFont();

            x -= this.getInnerX();//换算相对坐标
            int lineStart = 0;
            int lineEnd = editBuffer.length();
            if(multiLine) {
                y -= this.getInnerY();
                int lineHeight =(int)this.getFontSize();
                int endIndex = lineEnd;
                for(;;) {
                    lineEnd = computeLineEnd(lineStart);

                    if(lineStart >= endIndex || y < lineHeight) {
                        break;
                    }

                    lineStart = Math.min(lineEnd + 1, endIndex);
                    y -= lineHeight;
                }
            }
            return computeCursorPosFromX(x, lineStart, lineEnd);//根据第

    }
    protected int computeCursorPosFromX(int x, int lineStart) {
        return computeCursorPosFromX(x, lineStart, computeLineEnd(lineStart));
    }

    protected int computeCursorPosFromX(int x, int lineStart, int lineEnd) {
       // Font font = getFont();

            return lineStart +(int) (x/this.getFontSize());

    }
    protected int computeLineEnd(int cursorPos) {//得到行的末尾

        int endIndex = editBuffer.length();
        if(!multiLine) {
            return endIndex;
        }
        while(cursorPos < endIndex && editBuffer.charAt(cursorPos) != '\n') {
            cursorPos++;
        }
        return cursorPos;
    }

    @Override
    public boolean handleEvent(Event evt) {
        boolean selectPressed = (evt.getModifiers() & Event.MODIFIER_SHIFT) != 0;

        if(evt.isMouseEvent()) {
            boolean hover = (evt.getType() != Event.Type.MOUSE_EXITED) && isMouseInside(evt);
           // getAnimationState().setAnimationState(STATE_HOVER, hover);
        }

        if(evt.isMouseDragEvent()) {
            if(evt.getType() == Event.Type.MOUSE_DRAGGED &&
                    (evt.getModifiers() & Event.MODIFIER_LBUTTON) != 0) {
                int newPos = getCursorPosFromMouse(evt.getMouseX(), evt.getMouseY());
                setCursorPos(newPos, true);
            }
            return true;
        }

        if(super.handleEvent(evt)) {
            return true;
        }

       /* if(autoCompletionWindow != null) {
            if(autoCompletionWindow.handleEvent(evt)) {
                return true;
            }
        }*/

        switch (evt.getType()) {
            case KEY_PRESSED:
                switch (evt.getKeyCode()) {
                    case Event.KEY_BACK:
                        deletePrev();
                        return true;
                    case Event.KEY_DELETE:
                        deleteNext();
                        return true;
                    case Event.KEY_NUMPADENTER:
                    case Event.KEY_RETURN:
                        if(multiLine) {
                            if(evt.hasKeyCharNoModifiers()) {
                                insertChar('\n');
                            } else {
                                break;
                            }
                        } else {
                            //doCallback(Event.KEY_RETURN);
                        }
                        return true;
                    case Event.KEY_ESCAPE:
                        //doCallback(evt.getKeyCode());
                        return true;
                    case Event.KEY_HOME:
                        setCursorPos(computeLineStart(cursorPos), selectPressed);
                        return true;
                    case Event.KEY_END:
                        setCursorPos(computeLineEnd(cursorPos), selectPressed);
                        return true;
                    case Event.KEY_LEFT:
                        moveCursor(-1, selectPressed);
                        return true;
                    case Event.KEY_RIGHT:
                        moveCursor(+1, selectPressed);
                        return true;
                    case Event.KEY_UP:
                        if(multiLine) {
                            moveCursorY(-1, selectPressed);
                            return true;
                        }
                        break;
                    case Event.KEY_DOWN:
                        if(multiLine) {
                            moveCursorY(+1, selectPressed);
                            return true;
                        }
                        break;
                    case Event.KEY_TAB:
                        return false;
                    default:
                        if(evt.hasKeyCharNoModifiers()) {
                            insertChar(evt.getKeyChar());
                            return true;
                        }
                }
                if(forwardUnhandledKeysToCallback) {
                    //doCallback(evt.getKeyCode());
                    return true;
                }
                return false;

            case KEY_RELEASED:
                switch (evt.getKeyCode()) {
                    case Event.KEY_BACK:
                    case Event.KEY_DELETE:
                    case Event.KEY_NUMPADENTER:
                    case Event.KEY_RETURN:
                    case Event.KEY_ESCAPE:
                    case Event.KEY_HOME:
                    case Event.KEY_END:
                    case Event.KEY_LEFT:
                    case Event.KEY_RIGHT:
                        return true;
                    default:
                        return evt.hasKeyCharNoModifiers() || forwardUnhandledKeysToCallback;
                }

            case MOUSE_BTNUP:
                if(evt.getMouseButton() == Event.MOUSE_RBUTTON && isMouseInside(evt)) {
                   // showPopupMenu(evt);
                    return true;
                }
                break;

            case MOUSE_BTNDOWN:
                if(evt.getMouseButton() == Event.MOUSE_LBUTTON && isMouseInside(evt)) {
                    int newPos = getCursorPosFromMouse(evt.getMouseX(), evt.getMouseY());
                    setCursorPos(newPos, selectPressed);
                    scrollPos = 0;//textRenderer.lastScrollPos;
                    return true;
                }
                break;

            case MOUSE_CLICKED:
                if(evt.getMouseClickCount() == 2) {
                    int newPos = getCursorPosFromMouse(evt.getMouseX(), evt.getMouseY());
                    selectWordFromMouse(newPos);
                    this.cursorPos = selectionStart;
                    scrollToCursor(false);
                    this.cursorPos = selectionEnd;
                    scrollToCursor(false);
                    return true;
                }
                if(evt.getMouseClickCount() == 3) {
                    selectAll();
                    return true;
                }
                break;

            case MOUSE_WHEEL:
                return false;
        }

        return evt.isMouseEvent();
    }
    public void handle(){

    }
    public void onMouseMoveOver(){
        Window.cursor= Window.SELECT;
    }
    public void onMouseMoveOut(){
        Window.cursor= Window.POINT;
    }
    public void onFocus(){
        Window.cursor= Window.SELECT;
    }
    public void onClick(){
        Window.cursor= Window.SELECT;
    }

    protected void moveCursorY(int dir, boolean select) {
        if(multiLine) {
            int x = computeRelativeCursorPositionX(cursorPos);
            int lineStart;
            if(dir < 0) {
                lineStart = computeLineStart(cursorPos);
                if(lineStart == 0) {
                    setCursorPos(0, select);
                    return;
                }
                lineStart = computeLineStart(lineStart - 1);
            } else {
                lineStart = Math.min(computeLineEnd(cursorPos) + 1, editBuffer.length());
            }
            setCursorPos(computeCursorPosFromX(x, lineStart), select);
        }
    }
    @Override
    public void shaderRender(){//你好
     //  this.setMarginTop( (short)(this.getMarginTop()+10));
       // this.setMarginLeft( (short)(this.getMarginLeft()+10));
        this.innerText =editBuffer.toString();

        super.shaderRender();
        //绘制鼠标
        if(multiLine){
           /* String[] ss = innerText.split("\n");
            int count =0;
            for(String s:ss){
                ShaderUtils.draw2dColor(new Vector4f(1,1,1,1),this.getInnerX()+ (int)(this.cursorPos*this.getFontSize()),this.getInnerY(),10,(int)this.getFontSize());
                count++;

            }*/
                if(selectionStart!=selectionEnd){

                ShaderUtils.draw2dColor(new Vector4f(1,1,1,1),this.getInnerX()+ (int)(this.selectionStart*this.getFontSize()),this.getInnerY(),index+0.0008f,(selectionEnd-selectionStart)*(int)getFontSize(),(int)this.getFontSize());
                    //这里涉及到了分段
                    //开始的选中位置
                    //多段集合 每个集合都标明了 开始结束位置
                    //linestart lineend
                    //this.computeLineStart()
            }else{
                int preY  =0;
                int preX =0;
                int start=0;
                preY=0;
                String s= editBuffer.substring(0,this.cursorPos);
                    int thieLineStart =0;
                int totalLen=0;
                while((start=s.indexOf('\n',start+1))!=-1){
                    thieLineStart= start;
                    preY++;
                   // s= s.substring(start);
                    //totalLen+=start;
                }
                preX = this.cursorPos-thieLineStart;

                ShaderUtils.draw2dColor(new Vector4f(1,1,1,1),this.getInnerX()+ (int)(preX*this.getFontSize()),(int)(this.getInnerY()+preY*getFontSize()),index+0.0008f,10,(int)this.getFontSize());
            }
        }else{
            if(selectionStart!=selectionEnd){
                ShaderUtils.draw2dColor(new Vector4f(1,1,1,1),this.getInnerX()+ (int)(this.selectionStart*this.getFontSize()),this.getInnerY(),index+0.0008f,(selectionEnd-selectionStart)*(int)getFontSize(),(int)this.getFontSize());
            }else{
                ShaderUtils.draw2dColor(new Vector4f(1,1,1,1),this.getInnerX()+ (int)(this.cursorPos*this.getFontSize()),this.getInnerY(),index+0.0008f,10,(int)this.getFontSize());
            }
        }


    }
    final EditFieldModel editBuffer =new DefaultEditFieldModel();
    protected void insertChar(char ch) {
        // don't add control characters
        if(!readOnly && (!Character.isISOControl(ch) || (multiLine && ch == '\n'))) {
            boolean update = false;
            if(hasSelection()) {
                deleteSelection();
                update = true;
            }
            if(editBuffer.length() < maxTextLength) {
                if(editBuffer.replace(cursorPos, 0, ch)) {
                    cursorPos++;
                    update = true;
                }
            }
            if(update) {
                updateText(true, false, Event.KEY_NONE);
            }
        }
    }

    protected void moveCursor(int dir, boolean select) {
        setCursorPos(cursorPos + dir, select);
    }
    public boolean hasSelection() {
        return selectionStart != selectionEnd;
    }
    private boolean readOnly;
    protected void deletePrev() {
        if(!readOnly) {
            if(hasSelection()) {
                deleteSelection();
               // updateText(true, false, Event.KEY_DELETE);
            } else if(cursorPos > 0) {
                --cursorPos;
                deleteNext();
            }
        }
    }


    protected void deleteNext() {
        if(!readOnly) {
            if(hasSelection()) {
                deleteSelection();
              updateText(true, false, Event.KEY_DELETE);
            } else if(cursorPos < editBuffer.length()) {
                //innerText= innerText.substring(0,cursorPos)+innerText.substring(cursorPos+1);
                if(editBuffer.replace(cursorPos, 1, "") >= 0) {
                    updateText(true, false, Event.KEY_DELETE);
                }
            }
        }
    }
    private void updateText(boolean updateAutoCompletion, boolean fromModel, int key) {
        Document.needUpdate=true;
    }
    protected void deleteSelection() {
      //  innerText=  innerText.substring(0,selectionStart)+innerText.substring(selectionEnd);
       // Document.needUpdate=true;
        if(editBuffer.replace(selectionStart, selectionEnd-selectionStart, "") >= 0) {
            setCursorPos(selectionStart, false);
        }
    }

    protected void selectWordFromMouse(int index) {
        selectionStart = index;
        selectionEnd = index;
        while(selectionStart > 0 && !Character.isWhitespace(editBuffer.charAt(selectionStart-1))) {
            selectionStart--;
        }
        while(selectionEnd < editBuffer.length() && !Character.isWhitespace(editBuffer.charAt(selectionEnd))) {
            selectionEnd++;
        }
        updateSelection();
    }
    protected void setCursorPos(int pos, boolean select) {
        pos = Math.max(0, Math.min(editBuffer.length(), pos));
        if(!select) {
            boolean hadSelection = hasSelection();
            selectionStart = pos;
            selectionEnd = pos;
            if(hadSelection) {
                updateSelection();
            }
        }
        if(this.cursorPos != pos) {
            if(select) {
                if(hasSelection()) {
                    if(cursorPos == selectionStart) {
                        selectionStart = pos;
                    } else {
                        selectionEnd = pos;
                    }
                } else {
                    selectionStart = cursorPos;
                    selectionEnd = pos;
                }
                if(selectionStart > selectionEnd) {
                    int t = selectionStart;
                    selectionStart = selectionEnd;
                    selectionEnd = t;
                }
                updateSelection();
            }

            if(this.cursorPos != pos) {
                //getAnimationState().resetAnimationTime(STATE_CURSOR_MOVED);
            }
            this.cursorPos = pos;
            scrollToCursor(false);
            //updateAutoCompletion();
        }
    }

    protected void scrollToCursor(boolean force) {
        Document.needUpdate=true;
        int renderWidth = (int)(editBuffer.length()*this.getFontSize()) ;
        if(renderWidth <= 0) {
            pendingScrollToCursor = true;
            pendingScrollToCursorForce = force;
            return;
        }
        pendingScrollToCursor = false;
        int xpos = computeRelativeCursorPositionX(cursorPos);
        if(xpos < scrollPos + 5) {
            scrollPos = Math.max(0, xpos - 5);
        } else if(force || xpos - scrollPos > renderWidth) {
            scrollPos = Math.max(0, xpos - renderWidth);
        }
        if(multiLine) {
           /* ScrollPane sp = ScrollPane.getContainingScrollPane(this);
            if(sp != null) {
                int lineHeight = getLineHeight();
                int lineY = computeLineNumber(cursorPos) * lineHeight;
                sp.validateLayout();
                sp.scrollToAreaY(lineY, lineHeight, lineHeight/2);
            }*/
        }
    }


    protected int computeRelativeCursorPositionX(int cursorPos) {
        int lineStart = 0;
        if(multiLine) {
            lineStart = computeLineStart(cursorPos);
        }
        return (int) ((cursorPos-lineStart)*getFontSize());
       // return textRenderer.computeRelativeCursorPositionX(lineStart, cursorPos);
    }

    protected int computeLineStart(int cursorPos) {
        if(!multiLine) {
            return 0;
        }

        while(cursorPos > 0 && editBuffer.charAt(cursorPos-1) != '\n') {
            cursorPos--;
        }
        return cursorPos;
    }

    public void selectAll() {
        selectionStart = 0;
        selectionEnd = editBuffer.length();
        updateSelection();
    }

    public void pasteFromClipboard() {
        String cbText = Clipboard.getClipboard();
        if(cbText != null) {
            if(!multiLine) {
                cbText = TextUtil.stripNewLines(cbText);
            }
            insertText(cbText);
        }
    }
    public String getSelectedText() {
        return editBuffer.substring(selectionStart, selectionEnd);
    }
    public String getText() {
        return editBuffer.toString();
    }
    public void copyToClipboard() {
        String text;
        if(hasSelection()) {
            text = getSelectedText();
        } else {
            text = getText();
        }
        if(isPasswordMasking()) {
            text = TextUtil.createString(passwordChar, text.length());
        }
        Clipboard.setClipboard(text);
    }
    static class PasswordMasker implements CharSequence {
        final CharSequence base;
        final char maskingChar;

        public PasswordMasker(CharSequence base, char maskingChar) {
            this.base = base;
            this.maskingChar = maskingChar;
        }

        public int length() {
            return base.length();
        }

        public char charAt(int index) {
            return maskingChar;
        }

        public CharSequence subSequence(int start, int end) {
            throw new UnsupportedOperationException("Not supported.");
        }
    }
    private PasswordMasker passwordMasking;
    public boolean isPasswordMasking() {
        return passwordMasking != null;
    }
    public void insertText(String str) {
        if(!readOnly) {
            boolean update = false;
            if(hasSelection()) {
                deleteSelection();
                update = true;
            }
            int insertLength = Math.min(str.length(), maxTextLength - editBuffer.length());
            if(insertLength > 0) {
                int inserted = editBuffer.replace(cursorPos, 0, str.substring(0, insertLength));
                if(inserted > 0) {
                    cursorPos += inserted;
                    update = true;
                }
            }
            if(update) {
                updateText(true, false, Event.KEY_NONE);
            }
        }
    }

    public void cutToClipboard() {
        String text;
        if(!hasSelection()) {
            selectAll();
        }
        text = getSelectedText();
        if(!readOnly) {
            deleteSelection();
            updateText(true, false, Event.KEY_DELETE);
        }
        if(isPasswordMasking()) {
            text = TextUtil.createString(passwordChar, text.length());
        }
        Clipboard.setClipboard(text);
    }

    public void duplicateLineDown() {
        if(multiLine && !readOnly) {
            int lineStart, lineEnd;
            if(hasSelection()) {
                lineStart = selectionStart;
                lineEnd   = selectionEnd;
            } else {
                lineStart = cursorPos;
                lineEnd   = cursorPos;
            }
            lineStart = computeLineStart(lineStart);
            lineEnd   = computeLineEnd(lineEnd);
            String line = editBuffer.substring(lineStart, lineEnd);
            line = "\n".concat(line);
            editBuffer.replace(lineEnd, 0, line);
            setCursorPos(cursorPos + line.length());
            updateText(true, false, Event.KEY_NONE);
        }
    }

    public void setCursorPos(int pos) {
        if(pos < 0 || pos > editBuffer.length()) {
            throw new IllegalArgumentException("pos");
        }
        setCursorPos(pos, false);
    }


}
