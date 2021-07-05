package lex_erro_log;

public class LexerErrorDiag implements LexLog {

    
    private final String buffer;

    public LexerErrorDiag(String buffer) {
        this.buffer = buffer;
    }

    @Override
    public boolean log(int actualIdx, int newLineIdx) { // can throw? right?
        try {
            var beginIdx = actualIdx - newLineIdx;
            var cutErrorText = buffer.substring(beginIdx); // int x: 10\n other lines right?
            int i = 0;
            for (;i < cutErrorText.length(); i++) {
                if (cutErrorText.charAt(i) == '\n')
                    break;
            }
            System.out.println(buffer.substring(beginIdx, i + beginIdx));
            for (int j = 1; j < newLineIdx; j++) {
                System.out.print(' ');
            }
            System.out.println("~");
            return true;
        } catch (IndexOutOfBoundsException exception) {
            return false;
        }
    }

    public String buffer() {
        return buffer;
    }
}
