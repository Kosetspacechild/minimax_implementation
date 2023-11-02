/**
 * Solution code for Comp24011 Reversi lab
 *
 * @author h52199sh
 */

public class AlphaBetaMoveChooser extends MoveChooser {
    /**
     * MoveCooser implementation AlphaBetaMoveChooser(int)
     *
     * @param   searchDepth The requested depth for minimax search
     */
    public AlphaBetaMoveChooser(int searchDepth) {
        // Add object initialisation code...
        super("MyAwesomeAgent",searchDepth);
    }

    /**
     * Need to implement chooseMove(BoardState,Move)
     *
     * @param   boardState  The current state of the game board
     *
     * @param   hint        Skip move or board location clicked on the UI
     *                      This parameter should be ignored!
     *
     * @return  The move chosen by alpha-beta pruning as discussed in the course
     */

    public Move chooseMove(BoardState boardState, Move hint) {
        // Add alpha-beta pruning code...
        Move bestMove = new Move();
        if(boardState.colour == 1){                                                                 //if maxing
            int bestVal = Integer.MIN_VALUE;
            for (Move move : boardState.getLegalMoves()){
                BoardState daughters = boardState.deepCopy();
                daughters.makeLegalMove(move);
                int val = minimax(searchDepth - 1, daughters, Integer.MIN_VALUE, Integer.MAX_VALUE);
                if (val > bestVal){
                    bestVal = Math.max(val, bestVal);
                    bestMove = move;
                }
            }
        }
        
        else {
            int bestVal = Integer.MAX_VALUE;
            for (Move move : boardState.getLegalMoves()){
                BoardState daughters = boardState.deepCopy();
                daughters.makeLegalMove(move);
                int val = minimax(searchDepth - 1, daughters, Integer.MIN_VALUE, Integer.MAX_VALUE);
                if (val < bestVal){
                    bestVal = Math.min(val, bestVal);
                    bestMove = move;
                }    
            }
        }
        return bestMove;
    }

    private int minimax(int depth, BoardState board, int alpha, int beta){
        if (depth == 0 || board.gameOver()){
            return boardEval(board);
        }
        if (board.colour == 1){                                                     //if maxing
            alpha = Integer.MIN_VALUE;
            for (Move move : board.getLegalMoves()){
                if(alpha >= beta){
                    break;
                 }
                 BoardState daughters = board.deepCopy();
                 daughters.makeLegalMove(move);
                 alpha = Math.max(alpha, minimax(depth - 1, daughters, alpha, beta));
                }      
            return alpha;
         }
         else{
            beta = Integer.MAX_VALUE;
            for (Move move : board.getLegalMoves()){
                if(alpha >= beta){
                    break;
                 }
                 BoardState daughters = board.deepCopy();
                 daughters.makeLegalMove(move);
                 beta = Math.min(beta, minimax(depth - 1, daughters, alpha, beta));
                } 
            return beta;
         }
    }

    /**
     * Need to implement boardEval(BoardState)
     *
     * @param   boardState  The current state of the game board
     *
     * @return  The value of the board using Norvig's weighting of squares
     */
    public int boardEval(BoardState boardState) {
        // Add board evaluation code...
        int[][] boardEval = {
        {120, -20, 20, 5, 5, 20, -20, 120},
        {-20, -40, -5, -5, -5, -5, -40, -20},
        {20, -5, 15, 3, 3, 15, -5, 20},
        {5, -5, 3, 3, 3, 3, -5, 5},
        {5, -5, 3, 3, 3, 3, -5, 5},
        {20, -5, 15, 3, 3, 15, -5, 20},
        {-20, -40, -5, -5, -5, -5, -40, -20},
        {120, -20, 20, 5, 5, 20, -20, 120}
        };
        
        int totalEval = 0;

        for (int i = 0; i < 8; i++){
            for (int j = 0; j < 8; j++){
                if (boardState.getContents(i, j) == 1){
                    totalEval += boardEval[i][j];
                }
                if (boardState.getContents(i, j) == -1){
                    totalEval -= boardEval[i][j];
                }
            }
        }

        return totalEval;
    }
}

/* vim:set et ts=4 sw=4: */
