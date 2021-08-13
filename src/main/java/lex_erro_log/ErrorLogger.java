package lex_erro_log;

public class ErrorLogger {

    private ErrorLogger() {  }

    public static boolean log(String buffer, int actualIdx, int newLineIdx) { // can throw? right?
        try {
            var beginIdx = actualIdx - newLineIdx;
            if (beginIdx < 0) beginIdx *= -1;
            var cutErrorText = buffer.substring(beginIdx); // int x: 10\n other lines right?
            int i = 0;
            for (; i < cutErrorText.length(); i++) {
                if (cutErrorText.charAt(i) == '\n')
                    break;
            }
            System.out.println(buffer.substring(beginIdx, i + beginIdx));
            for (int j = 1; j < newLineIdx; j++) {
                System.out.print(' ');
            }
            System.out.println("^");
            return true;
        } catch (IndexOutOfBoundsException exception) {
            return false;
        }
    }
}
