package comp1110.ass2;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;

import java.util.Arrays;
import java.util.HashSet;

import static org.junit.Assert.assertTrue;

public class GenerateMoveTest {
    @Rule
    public Timeout globalTimeout = Timeout.millis(5000);

    /**
     * Only A4 is available to place a piece.
     */
    @Test
    public void testOneSquare() {
        String diceRoll = "A0A0A0B1";
        String boardString = "A0F00A0B00A0A31B1A14A0B61A0F61A0G32B1D61A0G43A0A62A0E61B1G56S1G60A0E03A0A03B1G12A0G02S0A50A0B50A5D03B1B40";
        String[] validMoves = {"S4A42", "A0A40", "B1A42", "B1A45", "B1A44"};

        testMove(diceRoll, boardString, validMoves);
    }

    /**
     * All exits are taken. Only B1 is free to continue a route.
     */
    @Test
    public void testNoExits() {
        String diceRoll = "A0A0A5B1";
        String boardString = "A5D00A0B03A0A30B1A10S3A20A0F03A0G32A5G53B1F60A5D61A0B61A0G43B1A54S1A40A0B40A0B31S4A60A5C62B1G12A0G01A0C00A5E62B1B23";
        String[] validMoves = {"A5B12B1C10A0C20", "B1B11A0C11A0C23", "A5B11", "A5B12B1C14", "B1B11A0C11A0C20", "A5B12B1C10A0C23A0D21", "A5B12B1C10A0C23A0D20", "B1B15", "B1B11A0C10"};
        testMove(diceRoll, boardString, validMoves);
    }

    /**
     * Exit D0 is free, and a route can be continued at B2.
     */
    @Test
    public void testOneExit() {
        String diceRoll = "A0A0A0B1";
        String boardString = "A0F00A0B00A0A30B1A10A0B61A0F61A0G32B1D61A0G43A0A62A0E61B1G56S1G60S5A20A0E03A0A03B1G12A0G02S0A50A0B50A0B41B1A41";
        String[] validMoves = {"B1B24A0B12A0C11A0C23", "B1D03A0C02A0C10A0B12", "B1D07", "B1D03A0C02A0C13A0D11", "B1D03A0C02A0C13A0D10", "B1B24A0B11", "B1D03A0C03", "B1B20A0B33A0C31A0C43", "B1B24A0B12A0C10A0C01", "B1B20A0B33A0C31A0C40", "B1B24A0B12A0C10A0C02", "B1D03A0C02A0C10A0B13", "B1B20A0B33A0C30A0C21", "B1B20A0B30", "B1B20A0B33A0C30A0C22", "B1B24A0B12A0C11A0C20",};
        testMove(diceRoll, boardString, validMoves);
    }

    /**
     * Only two options, but many possible orderings of each move.
     */
    @Test
    public void testTwoOptions() {
        String diceRoll = "A1A1A4B0";
        String boardString = "A4A10A1A30A4A50S1B32A1B01A1B61B2B10A1B21S5B50A1B41A4D01A4D61A3D12B0C30A3D50A4C10A4C50A1F01A1F61A4G10B1F12A4G50B1E10A1E21A3E52B1F56S4E31";
        String[] validMoves = {"A1G30A4E41B0D32A1F30", "A4E41A1F30B0D32A1G30", "A1F30A1G30A4E41B0D32", "A4E41A1G30B0D32A1F30", "B0F32A4E41", "A1F30B0D32A1G30A4E41", "A4E41B0G30", "B0D32A1F30A1G30A4E41", "B0D32A1G30A1F30A4E41", "A1F30A4E41A1G30B0D32", "A4E41B0D32A1G30A1F30", "A1G30B0D32A1F30A4E41", "A4E41B0D32A1F30A1G30", "A4E41B0F32", "A4E41A1F30A1G30B0D32", "A1F30A1G30B0D32A4E41", "A1G30A4E41A1F30B0D32", "B0D32A4E41A1F30A1G30", "B0D32A4E41A1G30A1F30", "A1G30B0D32A4E41A1F30", "B0D32A1F30A4E41A1G30", "A4E41A1G30A1F30B0D32", "A1G30A1F30A4E41B0D32", "A1F30B0D32A4E41A1G30", "B0D32A1G30A4E41A1F30", "B0G30A4E41", "A1G30A1F30B0D32A4E41", "A1F30A4E41B0D32A1G30"};

        testMove(diceRoll, boardString, validMoves);
    }

    /**
     * A random selection of highly-constrained positions.
     */
    @Test
    public void testManyOptions() {
        for (int i = 0; i < BOARD_STRINGS.length; i++) {
            String boardString = BOARD_STRINGS[i];
            String diceRoll = DICE_ROLLS[i];
            testMove(diceRoll, boardString, VALID_MOVES[i]);
        }
    }

    private void testMove(String diceRoll, String boardString, String[] validMoves) {
        HashSet<String> moves = new HashSet<>(Arrays.asList(validMoves));
        String move = RailroadInk.generateMove(boardString, diceRoll);
        assertTrue("RailroadInk.generateMove(\"" + boardString + "\", \"" + diceRoll + "\") returned invalid move: " + move, moves.contains(move));
    }

    private String[] BOARD_STRINGS = {"A4A50A0B61A3D60B1F60S0E61A0E52A5G52A4G61B2B50A0A30A1A21A5A10B2G31A3G41B0G12A0F13A2F01S1D03A2B02A1C00B0A00A3G21S3G00A1E00A4C50B1D50A0F51",
            "A3A53S0A44B1A35A0B03A0F07A0B61A4A61A1C00B0D02S4E00A4G10A4F10B1E12A2G31S3G20A4A10A4B10A4C10B0D10A5D62A0F61B1G56A2E67B0G41A2F22",
            "A1A30A1G30B0F30A4E30S2D30A1B30B0C32A4D41A1B61S1B50A1B01S5B10A1B21A4A10B0C10A1B41A1F61A4D61B0F53A4D51B0D21A1D11A1F01A4G10A1F11B0D03A1F21",
            "A3A10B2A31A1B30A0F61A4A21B1B14A4A41A4D61S2A50A5A63A2B01A1C02B0G52S0B63A0E63A2E51A4D51B0C32A5D31A5C61A0E41S5D41B1D03A5B51A4G10A0C42B0G30"};

    private String[] DICE_ROLLS = {"A5A5A5B1",
            "A5A1A5B0",
            "A1A1A4B0",
            "A4A4A4B1"};

    private String[][] VALID_MOVES = {
            {"B1B40A5C62A5A43", "A5C63B1F31A5F40A5E43", "A5C63B1F31A5F40A5E42", "B1D12A5E11A5E23A5C62", "B1C62", "B1D12A5E11A5E23A5C63", "B1D14A5C63A5C12A5C23", "B1B40A5A42A5C62", "B1C66", "B1D14A5C63A5C12A5C20", "B1D12A5C62A5E11A5E20", "A5C63B1A61", "B1A67A5C62", "B1B40A5A42A5C63", "B1A67A5C63", "B1D12A5C62A5E11A5E23", "B1B40A5A43A5C63", "A5C63B1A67", "B1B40A5A43A5C62", "A5C62B1D14A5C12A5C23", "B1B40A5C62A5A42", "B1F37A5F21A5E22A5C63", "B1F37A5F21A5E22A5C62", "A5C62B1D14A5C12A5C20", "A5C63B1D12A5E11A5E23", "A5C63B1B46A5C41", "A5C63B1D12A5E11A5E20", "B1D14A5C62A5C12A5C20", "A5C63B1B40A5A43", "B1D14A5C62A5C12A5C23", "A5C63B1B40A5A42", "B1F37A5C62A5F22", "B1F31A5F43A5C63", "A5C62B1D12A5E11A5E20", "B1F31A5F43A5C62", "A5C62B1A67", "A5C62B1D12A5E11A5E23", "B1B46A5C62A5C40A5C31", "B1D12A5C63A5E10", "A5C62B1A61", "B1B46A5C62A5C40A5C32", "A5C62B1B46A5C40A5C32", "A5C62B1B46A5C40A5C31", "B1F37A5F21A5E22A5E33", "B1D12A5C63A5E11A5E20", "B1F37A5F21A5E22A5E30", "B1D12A5C63A5E11A5E23", "B1F37A5C63A5F22", "A5C62B1D14A5C13", "B1D12A5E11A5E23A5F20", "B1D12A5E11A5E23A5F21", "A5C63B1D14A5C12A5C23", "A5C63B1D14A5C12A5C20", "A5C62B1F37A5F21A5E22", "A5C62B1F37A5F21A5E23", "B1B46A5C63A5C40A5C31", "A5C63B1F31A5F43", "B1B46A5C63A5C40A5C32", "B1B46A5C40A5C32A5D31", "B1B46A5C40A5C32A5D30", "B1B46A5C40A5C63A5C32", "B1B40A5C63A5A43", "B1B40A5C63A5A42", "B1B46A5C40A5C63A5C31", "B1F31A5C62A5F43", "B1F31A5C62A5F40A5E43", "A5C62B1B46A5C41", "B1B46A5C62A5C41", "B1F31A5C62A5F40A5E42", "B1D12A5E11A5C62A5E20", "B1D12A5E11A5E20A5D22", "B1D12A5E11A5E20A5D23", "B1D14A5C12A5C20A5B23", "B1D14A5C12A5C20A5B22", "B1D14A5C12A5C23A5C63", "B1D14A5C62A5C13", "B1D14A5C12A5C23A5C62", "B1A61A5C63", "B1A61A5C62", "B1B46A5C63A5C41", "B1F37A5C63A5F21A5E22", "B1D12A5E11A5C63A5E23", "B1F37A5C63A5F21A5E23", "A5C63B1F37A5F22", "A5C62B1D12A5E10", "A5C63B1B46A5C40A5C31", "A5C63B1D14A5C13", "A5C63B1B46A5C40A5C32", "B1F31A5F40A5C62A5E43", "B1F31A5F40A5C62A5E42", "A5C62B1F37A5F22", "B1B46A5C40A5C62A5C31", "B1B46A5C40A5C62A5C32", "B1B46A5C40A5C31A5B32", "B1B46A5C40A5C31A5B33", "B1F31A5F40A5E42A5C62", "B1F37A5F21A5C62A5E22", "B1F37A5F21A5C62A5E23", "B1D14A5C12A5C62A5C20", "B1F31A5F40A5E43A5E31", "B1F31A5F40A5E42A5C63", "B1F31A5F40A5E43A5E32", "B1D12A5E11A5C62A5E23", "B1D14A5C12A5C62A5C23", "B1B46A5C40A5C32A5C63", "B1B46A5C40A5C32A5C62", "B1D12A5E11A5E20A5C63", "B1D14A5C12A5C20A5C62", "B1D12A5E11A5E20A5C62", "B1D14A5C12A5C20A5C63", "B1D14A5C12A5C23A5D20", "B1D14A5C12A5C23A5D21", "B1F37A5C62A5F21A5E22", "B1F37A5C62A5F21A5E23", "B1F31A5F40A5C63A5E42", "A5C62B1F31A5F40A5E43", "B1F31A5F40A5C63A5E43", "B1F31A5C63A5F40A5E43", "B1F37A5F21A5E23A5E12", "B1D12A5E10A5C63", "B1F31A5C63A5F40A5E42", "A5C62B1F31A5F40A5E42", "B1B46A5C40A5C31A5C62", "B1D12A5C62A5E10", "B1D12A5E10A5C62", "B1F31A5F40A5E43A5C62", "B1B46A5C40A5C31A5C63", "B1F31A5F40A5E43A5C63", "B1F37A5F21A5E23A5E11", "B1B46A5C41A5C63", "B1B46A5C41A5C62", "B1F31A5C63A5F43", "B1D14A5C13A5C62", "B1D14A5C13A5C63", "B1F37A5F21A5C63A5E22", "A5C63B1F37A5F21A5E23", "B1F37A5F21A5C63A5E23", "B1D14A5C12A5C63A5C23", "B1D14A5C12A5C63A5C20", "B1F37A5F22A5C63", "B1D12A5E11A5C63A5E20", "B1F37A5F21A5E23A5C62", "B1F37A5F22A5C62", "B1F37A5F21A5E23A5C63", "A5C62B1F31A5F43", "A5C62B1B40A5A43", "B1D14A5C63A5C13", "A5C62B1B40A5A42", "A5C63B1D12A5E10", "A5C63B1F37A5F21A5E22"},
            {"A1E51B0B42A5C40A5C32", "B0B42A5C41A1E51A5C50", "B0E20A1G61A5D22A5D30", "A1B40B0E53A5E41A5D43", "B0E20A5D23A1E51", "B0B42A5C41A1E51A5C53", "A1E51B0B42A5C40A5C31", "B0E20A1G61A5D22A5D33", "A1E51B0E20A5D23", "A1E20B0G61", "A1B40B0E53A5E41A5D42", "B0E20A1G61A5D23", "A1E20B0E53A5E41A5D42", "A1E20B0E53A5E41A5D43", "B0B42A5C40A5C31A1G61", "B0B42A5C40A5C31A1E20", "B0B42A1G61A5C41A5C53", "A1E51B0G61", "B0E53A5E42A1B40A5F41", "B0E53A5E42A1B40A5F40", "B0B42A5C41A1E20A5C50", "B0E53A1B40A5E42A5F41", "B0E53A1B40A5E42A5F40", "B0B42A5C41A1E20A5C53", "B0E20A5D22A5D30A1G61", "A1E51B0B42A5C41A5C50", "B0B42A5C41A5C50A1E51", "B0B42A5C40A1E20A5C32", "B0B42A5C40A1E20A5C31", "A1E51B0E20A5D22A5D33", "A1E51B0E20A5D22A5D30", "B0E53A1G61A5E42A5F40", "B0B42A1E51A5C41A5C50", "B0E53A1G61A5E42A5F41", "B0B42A5C40A1G61A5C31", "B0B42A5C40A1G61A5C32", "B0E20A5D22A1G61A5D33", "B0E53A5E41A1G61A5D43", "B0E53A5E41A1G61A5D42", "A1G61B0E53A5E41A5D42", "B0G61A1B40", "B0E20A5D22A1G61A5D30", "A1E20B0D20A5C22A5C33", "A1G61B0E20A5D22A5D30", "A1G61B0E53A5E41A5D43", "A1G61B0E20A5D22A5D33", "B0G61A1E20", "A1E20B0B42A5C40A5C31", "A1E20B0B42A5C40A5C32", "B0E20A1E51A5D22A5D30", "A1E51B0B42A5C41A5C53", "B0B42A1E20A5C41A5C50", "B0E20A5D22A5D30A1B40", "B0E20A1E51A5D22A5D33", "B0E53A5E41A5D43A1G61", "B0E53A5E42A1G61A5F41", "B0E53A5E41A5D43A1E20", "B0E53A5E42A1G61A5F40", "B0E53A5E41A5D43A1B40", "B0E20A1E51A5D23", "A1G61B0B42A5C41A5C53", "B0B42A5C41A1G61A5C50", "B0B42A1G61A5C41A5C50", "B0B42A5C41A1G61A5C53", "B0B42A5C40A5C31A1E51", "B0B42A1E51A5C41A5C53", "B0B42A5C40A5C32A1E20", "B0B42A5C40A5C32A1G61", "A1B40B0E20A5D23", "A1B40B0E20A5D22A5D33", "B0B42A5C41A5C53A1E20", "A1B40B0E20A5D22A5D30", "A1B40B0C42A5D40A5D31", "A1G61B0B42A5C40A5C31", "A1G61B0B42A5C40A5C32", "A1B40B0E53A5E42A5F41", "A1B40B0E53A5E42A5F40", "A1G61B0B42A5C41A5C50", "B0E20A5D22A5D33A1B40", "B0E20A5D22A5D33A1G61", "A1B40B0C42A5D40A5D32", "A1E20B0D20A5C23", "A1E20B0E53A5E42A5F40", "B0B42A1E20A5C41A5C53", "B0E20A5D23A1G61", "B0E20A5D22A1E51A5D30", "A1E20B0E53A5E42A5F41", "B0E20A5D23A1B40", "A1B40B0C42A5D41A5D53", "B0E20A1B40A5D23", "B0G61A1E51", "A1B40B0C42A5D41A5D50", "A1G61B0E20A5D23", "B0E20A5D22A1E51A5D33", "A1E20B0D20A5C22A5C30", "B0E53A1E20A5E42A5F41", "B0B42A5C41A5C53A1G61", "B0E53A1E20A5E42A5F40", "B0E53A5E41A5D42A1E20", "B0E53A5E41A5D42A1G61", "B0E53A5E42A5F40A1G61", "B0E53A5E42A5F40A1E20", "A1E51B0E43A5E32A5F30", "A1E51B0E43A5E32A5F31", "B0E53A5E41A5D42A1B40", "B0E53A5E42A5F40A1B40", "B0B42A5C41A5C50A1E20", "B0E53A1E20A5E41A5D42", "B0B42A5C41A5C50A1G61", "B0B42A1E20A5C40A5C32", "B0E53A1E20A5E41A5D43", "B0B42A1E20A5C40A5C31", "B0E53A5E42A1E20A5F41", "B0E20A1B40A5D22A5D30", "B0E53A5E42A1E20A5F40", "A1B40B0G61", "A1E20B0B42A5C41A5C50", "B0B42A5C41A5C53A1E51", "B0E53A1G61A5E41A5D43", "B0E53A1G61A5E41A5D42", "A1G61B0E53A5E42A5F40", "A1G61B0E53A5E42A5F41", "B0E20A1B40A5D22A5D33", "B0B42A5C40A5C32A1E51", "B0E53A5E42A5F41A1B40", "B0E53A5E42A5F41A1G61", "A1E51B0E43A5E31A5D33", "B0B42A1E51A5C40A5C32", "A1E51B0E43A5E31A5D32", "B0B42A1E51A5C40A5C31", "B0E53A5E42A5F41A1E20", "B0E53A1B40A5E41A5D42", "A1E20B0B42A5C41A5C53", "B0E53A1B40A5E41A5D43", "B0E20A5D22A1B40A5D33", "B0E20A5D22A1B40A5D30", "B0E53A5E41A1B40A5D43", "B0E53A5E41A1B40A5D42", "B0E53A5E41A1E20A5D42", "B0E53A5E41A1E20A5D43", "B0B42A5C40A1E51A5C31", "B0B42A5C40A1E51A5C32", "B0B42A1G61A5C40A5C31", "B0B42A1G61A5C40A5C32", "B0E20A5D22A5D30A1E51", "B0E20A5D22A5D33A1E51"},
            {"B0F41A4G50A1C50", "A1C50B0F41A4A50", "B0C52A4F41", "A1C50B0F41A4G50", "A4G50A1C50B0F41", "B0G52A1C50A4A50", "A4A50B0G52A1C50", "A4A50B0F41A1C50", "A4F41A1C50B0G52", "A1C50B0G52A4F41", "B0F41A4A50A1C50", "A4A50A1C50B0F41", "A1C50A4A50B0G52", "A4F41B0C52", "B0G52A4A50A1C50", "A1C50A4F41B0G52", "B0G52A1C50A4F41", "B0C52A4G50", "A4G50B0C52", "B0C52A4A50", "A4A50B0C52", "A4G50B0F41A1C50", "B0G52A4F41A1C50", "B0F41A1C50A4G50", "B0F41A1C50A4A50", "A4A50A1C50B0G52", "A4F41B0G52A1C50", "A1C50A4A50B0F41", "A1C50A4G50B0F41", "A1C50B0G52A4A50"},
            {"B1F57A4F10A4E10A4F30", "A4F10A4F30A4E10B1F51", "A4F10A4F30A4E10B1D12", "A4F10A4F30A4E10B1E32", "A4F10A4F30A4E10B1F53", "A4F30A4E30B1F53A4F41", "A4F10A4E10B1F51A4F30", "A4F10A4F30A4E10B1F55", "A4F30B1F02A4E30A4G00", "A4F10A4F30A4E10B1D16", "A4F10A4F30A4E10B1E36", "A4F10A4F30A4E10B1F57", "B1F04A4F10A4F30A4E00", "A4F10A4E10B1C54A4D10", "B1F55A4F30A4F10A4E30", "B1F32A4F10A4E10A4D10", "B1F53A4F41A4F31A4F10", "A4F10A4E10A4D10B1C16", "A4F10B1F36A4E10A4D10", "A4F30B1E32A4F10A4E10", "B1F02A4F10A4E10A4D10", "B1F02A4G00A4F30A4E30", "A4F10A4E10B1F53A4D10", "B1F51A4F10A4F30A4E10", "A4F10A4E10A4D10B1C12", "A4F30A4F10B1F02A4E30", "B1F04A4F10A4E00A4E10", "B1F57A4F30A4F41A4F10", "B1F04A4F10A4E00A4F30", "B1F57A4F10A4E10A4F41", "A4F10B1F57A4F41A4F30", "B1F51A4F10A4F30A4E30", "A4F10A4F30A4E30B1F02", "B1F57A4F41A4F10A4E10", "B1F57A4F41A4F10A4F31", "B1F57A4F41A4F10A4F30", "A4F10A4F30A4E30B1F04", "B1C52A4F10A4E10A4D10", "A4F10B1F57A4F41A4E10", "A4F10B1F57A4F41A4F31", "A4F10A4F30B1E12A4E30", "B1F57A4F30A4E30A4F41", "A4F30A4F10A4E30B1E16", "B1C52A4F30A4F10A4E10", "B1F04A4E00A4F30A4F10", "A4F10A4E10B1C52A4D10", "A4F30A4F10B1F57A4E10", "B1C52A4F10A4F30A4E30", "A4F30A4F10A4E30B1E12", "A4F10A4E10B1F53A4F41", "A4F30A4F10B1F04A4E30", "B1F04A4F30A4E30A4F10", "A4F30B1F04A4E30A4F10", "B1F51A4F30A4E30A4F10", "A4F10B1F51A4E10A4F30", "B1F57A4F10A4E10A4D10", "A4F30A4E30B1C52A4F10", "A4F10A4E10B1C54A4F30", "B1F04A4F10A4E10A4E00", "A4F10A4F30B1E36A4E10", "A4F10B1F51A4F30A4E30", "A4F30A4E30A4F10B1F53", "A4F10A4E10A4D10B1F57", "A4F30A4E30A4F10B1F51", "B1F57A4F30A4E30A4F10", "A4F30B1F51A4F10A4E30", "A4F30A4E30A4F10B1F57", "A4F30A4F10B1F57A4F41", "B1F02A4F10A4E10A4F30", "A4F30A4E30A4F10B1F55", "A4F10A4E10B1F53A4F30", "A4F30B1F04A4E30A4E00", "A4F10A4E10A4D10B1F51", "B1F57A4F10A4F30A4F41", "A4F10A4E10A4D10B1F53", "A4F10A4E10A4D10B1F55", "A4F10A4F30B1E16A4E30", "A4F30A4F10B1F55A4E10", "A4F10A4E10A4F30B1E36", "A4F10A4E10A4F30B1F57", "A4F10A4E10A4F30B1D16", "A4F10A4E10B1F36A4D10", "B1F53A4F41A4F30A4E30", "A4F10B1C54A4E10A4D10", "B1F02A4F30A4F10A4E10", "B1F04A4F10A4F30A4E10", "B1F04A4F10A4E10A4F30", "A4F30A4F10B1E32A4E10", "A4F10A4E10A4F30B1F51", "A4F10A4E10A4F30B1E32", "A4F10A4E10A4F30B1F53", "A4F10A4E10A4F30B1D12", "A4F10A4E10A4F30B1F55", "B1F55A4F10A4E10A4D10", "B1F53A4F41A4F31A4F21", "A4F10B1E16A4F30A4E30", "A4F30A4F10A4E10B1C52", "B1F51A4F10A4E10A4F30", "A4F10A4E10B1F55A4D10", "A4F30A4F10B1F57A4E30", "A4F30A4F10A4E10B1C54", "A4F30B1F57A4E30A4F10", "A4F30A4F10B1F04A4E10", "A4F30A4E30B1F57A4F41", "B1F57A4F10A4F30A4E30", "A4F10B1F51A4E10A4D10", "A4F30B1F02A4G00A4E30", "B1F53A4F10A4F30A4E10", "B1F02A4F10A4F30A4E10", "A4F30B1F04A4F10A4E00", "A4F10A4F30B1F02A4E10", "A4F10B1F53A4E10A4F30", "A4F30B1C54A4E30A4F10", "A4F10A4F30A4E30B1E16", "A4F30A4F10B1F53A4E10", "B1F57A4F30A4F10A4F41", "A4F30A4F10B1C54A4E10", "A4F10A4F30A4E30B1E12", "A4F10A4F30B1C52A4E30", "A4F30A4F10B1E36A4E10", "A4F30B1F57A4E30A4F41", "A4F30A4F10A4E30B1F02", "A4F30B1F51A4F10A4E10", "A4F30A4F10A4E30B1F04", "A4F10A4E10B1F02A4G00", "A4F30A4E30A4F10B1E12", "B1F02A4F30A4G00A4F10", "A4F10A4E10A4D10B1C52", "A4F10B1C52A4F30A4E30", "A4F30A4E30A4F10B1E16", "A4F10A4E10A4D10B1C54", "A4F30B1F53A4F10A4E30", "B1F53A4F30A4E30A4F10", "B1F04A4F30A4F10A4E00", "A4F10B1C54A4E10A4F30", "A4F10A4F30B1F51A4E30", "A4F10B1F53A4E10A4F41", "A4F10B1F04A4E00A4E10", "B1F02A4F30A4F10A4E30", "A4F30B1F57A4F10A4E10", "B1F04A4F10A4E10A4D10", "B1F57A4F30A4F10A4E30", "A4F10B1F04A4E00A4F30", "A4F10A4F30B1F04A4E10", "B1F55A4F10A4E10A4F30", "A4F10A4E10B1F55A4F30", "A4F30A4F10B1C54A4E30", "A4F30B1F55A4E30A4F10", "B1F04A4F30A4E00A4E30", "A4F10A4F30A4E10B1C52", "A4F30B1F53A4F10A4F41", "B1F57A4F10A4F30A4E10", "A4F30B1F12A4E30", "A4F10A4F30B1F02A4E30", "B1F02A4F10A4F30A4E30", "A4F10A4F30A4E30B1F57", "A4F10B1F53A4E10A4D10", "A4F30A4F10B1F53A4E30", "B1F04A4E00A4F10A4F30", "A4F10A4F30A4E30B1F53", "A4F10A4F30A4E30B1F55", "A4F10A4F30B1F04A4E00", "B1F04A4E00A4F10A4E10", "A4F10A4F30B1C52A4E10", "A4F30A4E30B1F51A4F10", "A4F10A4F30A4E30B1F51", "A4F30A4E30B1F57A4F10", "B1F04A4F30A4E30A4E00", "A4F10B1F55A4F30A4E30", "B1C54A4F10A4F30A4E10", "A4F30A4F10A4E10B1F02", "B1C52A4F30A4E30A4F10", "A4F10B1F04A4E10A4D10", "A4F30A4F10A4E10B1F04", "A4F10B1F02A4F30A4E30", "A4F10B1F02A4E10A4G00", "A4F10B1F02A4G00A4E10", "A4F10A4F30B1F51A4E10", "A4F10B1E12A4F30A4E30", "A4F30B1F04A4F10A4E10", "A4F30A4F10B1F53A4F41", "B1C54A4F10A4F30A4E30", "A4F10B1F02A4G00A4F30", "A4F10B1C52A4F30A4E10", "A4F30B1F02A4E30A4F10", "B1C52A4F10A4E10A4F30", "A4F10A4F30B1F57A4E10", "B1F57A4F30A4F10A4E10", "B1F02A4F10A4E10A4G00", "A4F30A4E30A4F10B1F02", "A4F30B1F02A4F10A4G00", "A4F30A4E30A4F10B1F04", "A4F10B1F32A4E10A4D10", "B1F02A4F10A4G00A4F30", "B1F53A4F30A4E30A4F41", "B1F02A4F10A4G00A4E10", "A4F30B1F53A4E30A4F41", "B1F04A4F30A4F10A4E30", "A4F10A4F30B1F57A4F41", "A4F10A4F30B1F55A4E10", "A4F30B1F04A4E00A4F10", "B1C54A4F30A4F10A4E30", "A4F10A4E10B1F04A4F30", "B1F53A4F10A4E10A4D10", "A4F10B1F02A4F30A4E10", "A4F30B1F02A4F10A4E10", "A4F10B1F55A4F30A4E10", "A4F30B1F51A4E30A4F10", "B1F02A4G00A4F10A4F30", "B1F02A4G00A4F10A4E10", "A4F10B1F04A4E10A4F30", "A4F10B1F57A4E10A4F30", "B1F55A4F10A4F30A4E10", "B1F02A4F30A4G00A4E30", "A4F30A4F10B1C52A4E30", "A4F30B1F04A4F10A4E30", "B1F53A4F30A4F41A4F10", "A4F30B1F55A4F10A4E30", "A4F30B1F53A4F41A4F10", "A4F10A4F30B1C54A4E10", "A4F10A4E10A4F30B1C52", "A4F10A4F30B1F57A4E30", "A4F30A4F10B1E16A4E30", "A4F10B1F04A4F30A4E30", "B1F57A4F41A4F30A4E30", "A4F10A4E10A4F30B1C54", "B1F53A4F30A4F10A4E10", "A4F10A4F30B1F53A4E10", "A4F10B1C54A4F30A4E10", "B1F16A4F30A4E30", "A4F30A4F10B1F02A4G00", "B1F57A4F10A4F41A4F30", "A4F30A4F10A4E30B1C52", "A4F30B1C54A4F10A4E10", "B1F57A4F10A4F41A4E10", "A4F30A4F10A4E30B1C54", "B1F04A4F30A4E00A4F10", "B1F57A4F10A4F41A4F31", "A4F10B1F57A4E10A4F41", "A4F30A4E30B1F02A4F10", "A4F10B1F04A4E10A4E00", "A4F30B1C52A4E30A4F10", "A4F30B1F57A4F41A4E30", "A4F10A4F30A4E10B1C54", "B1F02A4F30A4E30A4G00", "B1F04A4F30A4F10A4E10", "B1F53A4F10A4F30A4E30", "A4F10A4F30B1F55A4E30", "B1F36A4F10A4E10A4D10", "A4F30B1F57A4F10A4F41", "B1F51A4F30A4F10A4E30", "A4F10A4E10B1F04A4D10", "A4F10B1F02A4E10A4F30", "B1F53A4F10A4E10A4F30", "B1F53A4F30A4F10A4F41", "B1C54A4F30A4F10A4E10", "A4F10B1F57A4F30A4F41", "A4F30A4F10B1F51A4E10", "A4F10B1F53A4F30A4E10", "A4F30B1F53A4F10A4E10", "B1F55A4F10A4F30A4E30", "A4F30A4E30B1F04A4F10", "A4F10B1F57A4E10A4D10", "A4F30A4F10B1C52A4E10", "B1F53A4F10A4F30A4F41", "A4F30B1F57A4F10A4E30", "A4F10A4F30B1F04A4E30", "A4F30B1E36A4F10A4E10", "B1F53A4F10A4E10A4F41", "B1F53A4F30A4F10A4E30", "A4F10B1F57A4F30A4E30", "A4F30A4F10B1E12A4E30", "A4F10B1C54A4F30A4E30", "A4F30A4F10B1F51A4E30", "A4F10A4E10B1D16A4F30", "A4F10A4E10B1F02A4F30", "A4F30B1C54A4F10A4E30", "B1F55A4F30A4E30A4F10", "A4F30A4E30B1F04A4E00", "A4F30A4E30B1F55A4F10", "A4F10A4E10B1F57A4F30", "B1F12A4F30A4E30", "A4F30B1F04A4E00A4E30", "A4F10B1F04A4F30A4E00", "A4F10A4E10B1D12A4F30", "B1F02A4F30A4F10A4G00", "A4F10B1F02A4E10A4D10", "A4F10A4F30A4E30B1C52", "B1F51A4F30A4F10A4E10", "A4F10B1F51A4F30A4E10", "A4F10A4F30A4E30B1C54", "A4F10A4E10A4D10B1F36", "A4F30B1C52A4F10A4E30", "B1F02A4G00A4F30A4F10", "A4F30A4E30A4F10B1C54", "B1F57A4F30A4F41A4E30", "A4F10B1F53A4F41A4F30", "A4F10B1F53A4F41A4E10", "A4F10B1F53A4F41A4F31", "A4F10B1F55A4E10A4D10", "A4F30A4E30B1C54A4F10", "A4F30A4F10B1F04A4E00", "A4F10B1F53A4F30A4E30", "A4F30A4F10B1F55A4E30", "A4F30B1F16A4E30", "A4F10A4E10A4D10B1F32", "A4F10A4F30B1E32A4E10", "A4F30A4E30B1F02A4G00", "A4F10B1F57A4F30A4E10", "A4F30B1F53A4F41A4E30", "A4F10B1C52A4E10A4F30", "A4F30A4E30B1F53A4F10", "B1F53A4F30A4F41A4E30", "B1F57A4F41A4F30A4F10", "B1F02A4F30A4E30A4F10", "B1F04A4F10A4F30A4E30", "A4F30A4E30A4F10B1C52", "B1F57A4F41A4F31A4F21", "B1C54A4F10A4E10A4F30", "B1F51A4F10A4E10A4D10", "A4F30A4F10A4E10B1F51", "A4F10B1F53A4F30A4F41", "A4F30A4F10A4E10B1D12", "A4F30A4F10A4E10B1E32", "A4F30A4F10A4E10B1F53", "A4F10A4E10B1F02A4D10", "A4F10A4E10B1F32A4D10", "A4F30A4F10A4E10B1F55", "A4F30A4F10A4E10B1D16", "A4F30A4F10A4E10B1E36", "A4F30A4F10A4E10B1F57", "A4F30B1F57A4F41A4F10", "B1F04A4E00A4F30A4E30", "A4F10A4E10A4F30B1F02", "A4F10A4E10B1F57A4D10", "B1F55A4F30A4F10A4E10", "A4F10A4E10A4F30B1F04", "B1F53A4F10A4F41A4F30", "A4F10A4E10B1F51A4D10", "B1F53A4F10A4F41A4E10", "B1F53A4F10A4F41A4F31", "B1F57A4F41A4F31A4F10", "A4F10A4F30B1F53A4F41", "A4F10B1F55A4E10A4F30", "A4F30B1F02A4F10A4E30", "A4F30A4F10B1F02A4E10", "A4F10A4E10B1F57A4F41", "A4F10A4F30B1C54A4E30", "A4F30A4E30B1F12", "A4F30B1F55A4F10A4E10", "A4F30A4E30B1F16", "B1F53A4F41A4F30A4F10", "B1F02A4F10A4F30A4G00", "A4F10A4F30A4E10B1F02", "A4F10B1C52A4E10A4D10", "A4F10A4F30A4E10B1F04", "A4F10B1F04A4F30A4E10", "A4F10A4F30B1F02A4G00", "A4F10A4E10B1F04A4E00", "A4F30B1C52A4F10A4E10", "A4F30B1F53A4E30A4F10", "A4F10A4E10A4D10B1F02", "A4F10A4F30B1F53A4E30", "A4F30A4F10A4E30B1F57", "B1C52A4F30A4F10A4E30", "B1F53A4F41A4F10A4E10", "B1F53A4F41A4F10A4F31", "B1F53A4F41A4F10A4F30", "A4F10A4E10A4D10B1F04", "A4F10B1F02A4F30A4G00", "B1C54A4F10A4E10A4D10", "A4F10A4E10B1C52A4F30", "B1C52A4F10A4F30A4E10", "A4F30A4F10A4E30B1F53", "A4F30A4F10A4E30B1F55", "A4F30A4F10A4E30B1F51", "B1C54A4F30A4E30A4F10", "A4F30B1F02A4G00A4F10"}
    };
}
