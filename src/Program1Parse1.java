import components.map.Map;
import components.program.Program;
import components.program.Program1;
import components.queue.Queue;
import components.set.Set;
import components.set.Set1L;
import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;
import components.statement.Statement;
import components.utilities.Reporter;
import components.utilities.Tokenizer;

/**
 * Layered implementation of secondary method {@code parse} for {@code Program}.
 *
 * @author Abhayjeet S., Wesam K., Pravin H.
 *
 *
 */
public final class Program1Parse1 extends Program1 {

    /*
     * Private members --------------------------------------------------------
     */

    /**
     * Parses a single BL instruction from {@code tokens} returning the
     * instruction name as the value of the function and the body of the
     * instruction in {@code body}.
     *
     * @param tokens
     *            the input tokens
     * @param body
     *            the instruction body
     * @return the instruction name
     * @replaces body
     * @updates tokens
     * @requires <pre>
     * [<"INSTRUCTION"> is a prefix of tokens]  and
     *  [<Tokenizer.END_OF_INPUT> is a suffix of tokens]
     * </pre>
     * @ensures <pre>
     * if [an instruction string is a proper prefix of #tokens]  and
     *    [the beginning name of this instruction equals its ending name]  and
     *    [the name of this instruction does not equal the name of a primitive
     *     instruction in the BL language] then
     *  parseInstruction = [name of instruction at start of #tokens]  and
     *  body = [Statement corresponding to the block string that is the body of
     *          the instruction string at start of #tokens]  and
     *  #tokens = [instruction string at start of #tokens] * tokens
     * else
     *  [report an appropriate error message to the console and terminate client]
     * </pre>
     */
    private static String parseInstruction(Queue<String> tokens,
            Statement body) {
        assert tokens != null : "Violation of: tokens is not null";
        assert body != null : "Violation of: body is not null";
        assert tokens.length() > 0 && tokens.front().equals("INSTRUCTION") : ""
                + "Violation of: <\"INSTRUCTION\"> is proper prefix of tokens";

        // Dequeue entry and check for syntax errors
        Reporter.assertElseFatalError(tokens.dequeue().equals("INSTRUCTION"),
                "Error: Missing 'INSTRUCTION'.");

        //CORRECTION: ADDED PRIMITIVES AND UPDATED CALL
        Set<String> primitives = new Set1L<>();
        primitives.add("move");
        primitives.add("turnleft");
        primitives.add("turnright");
        primitives.add("infect");
        primitives.add("skip");

        String callStart = tokens.dequeue();
        Reporter.assertElseFatalError(!primitives.contains(callStart),
                "Error: Missing Identifier.");

        Reporter.assertElseFatalError(tokens.dequeue().equals("IS"),
                "Error: Missing 'IS'.");

        // Parses block
        body.parseBlock(tokens);

        Reporter.assertElseFatalError(tokens.dequeue().equals("END"),
                "Error: Missing 'END'.");

        String callEnd = tokens.dequeue();

        Reporter.assertElseFatalError(callStart.equals(callEnd),
                "Error: Missing or mismatching Identifer.");

        return callEnd;
    }

    /*
     * Constructors -----------------------------------------------------------
     */

    /**
     * No-argument constructor.
     */
    public Program1Parse1() {
        super();
    }

    /*
     * Public methods ---------------------------------------------------------
     */

    @Override
    public void parse(SimpleReader in) {
        assert in != null : "Violation of: in is not null";
        assert in.isOpen() : "Violation of: in.is_open";
        Queue<String> tokens = Tokenizer.tokens(in);
        this.parse(tokens);
    }

    @Override
    public void parse(Queue<String> tokens) {
        assert tokens != null : "Violation of: tokens is not null";
        assert tokens.length() > 0 : ""
                + "Violation of: Tokenizer.END_OF_INPUT is a suffix of tokens";

        Reporter.assertElseFatalError(tokens.front().equals("PROGRAM"),
                "Error: Missing 'PROGRAM'.");
        tokens.dequeue();

        String nameProg = tokens.dequeue();
        Reporter.assertElseFatalError(Tokenizer.isIdentifier(nameProg),
                "Error: Missing Identifer.");

        Reporter.assertElseFatalError(tokens.front().equals("IS"),
                "Error: Missing 'IS'.");
        tokens.dequeue();

        Map<String, Statement> ctxt = this.newContext();

        while (tokens.front().equals("INSTRUCTION")) {
            Statement conBody = this.newBody();
            String statemen = parseInstruction(tokens, conBody);
            Reporter.assertElseFatalError(!ctxt.hasKey(statemen),
                    "Error: Duplicate user defined instructions.");
            ctxt.add(statemen, conBody);
        }

        this.swapContext(ctxt);

        Reporter.assertElseFatalError(tokens.front().equals("BEGIN"),
                "Error: Missing 'BEGIN'.");
        tokens.dequeue();

        Statement body = this.newBody();
        body.parseBlock(tokens);
        this.swapBody(body);

        Reporter.assertElseFatalError(tokens.front().equals("END"),
                "Error: Missing 'END'.");
        tokens.dequeue();

        Reporter.assertElseFatalError(tokens.front().equals(nameProg),
                "Error: Missing or mismatching Identifier.");
        tokens.dequeue();

        Reporter.assertElseFatalError(
                tokens.front().equals(Tokenizer.END_OF_INPUT),
                "Error: Extra inputs at the end of the program.");

        this.setName(nameProg);
    }

    /*
     * Main test method -------------------------------------------------------
     */

    /**
     * Main method.
     *
     * @param args
     *            the command line arguments
     */
    public static void main(String[] args) {
        SimpleReader in = new SimpleReader1L();
        SimpleWriter out = new SimpleWriter1L();
        /*
         * Get input file name
         */
        out.print("Enter valid BL program file name: ");
        String fileName = in.nextLine();
        /*
         * Parse input file
         */
        out.println("*** Parsing input file ***");
        Program p = new Program1Parse1();
        SimpleReader file = new SimpleReader1L(fileName);
        Queue<String> tokens = Tokenizer.tokens(file);
        file.close();
        p.parse(tokens);
        /*
         * Pretty print the program
         */
        out.println("*** Pretty print of parsed program ***");
        p.prettyPrint(out);

        in.close();
        out.close();
    }

}
