package student_player;

import boardgame.Move;

import java.util.List;
import java.util.Random;

import pentago_swap.PentagoPlayer;
import pentago_swap.PentagoBoardState;
import pentago_swap.PentagoCoord;
import pentago_swap.PentagoBoardState.Piece;
import pentago_swap.PentagoMove;

/** A player file submitted by a student. */
public class StudentPlayer extends PentagoPlayer {

    // Declaring constants
	// Max depth of the minimax tree (Optimum value to stay in time limit: 3)
	final int MAX_DEPTH = 3; 
	// Required for the alpha-beta pruning
	final int DEFAULT_ALPHA = Integer.MIN_VALUE; 
	final int DEFAULT_BETA = Integer.MAX_VALUE;
	
	// player and opponent id
	private int player;
    private int opponent;
    
    /**
     * You must modify this constructor to return your student number. This is
     * important, because this is what the code that runs the competition uses to
     * associate you with your agent. The constructor should do nothing else.
     */
    public StudentPlayer() {
        super("260663521");
    }

    /**
     * This is the primary method that you need to implement. The ``boardState``
     * object contains the current state of the game, which your agent must use to
     * make decisions.
     */
    public Move chooseMove(PentagoBoardState boardState) {
        // You probably will make separate functions in MyTools.
        // For example, maybe you'll need to load some pre-processed best opening
        // strategies...

        //old stuff
        // MyTools.getSomething();
        // Move myMove = boardState.getRandomMove();

        if (boardState.getTurnNumber() == 0) { 
			return boardState.getRandomMove();
        }
        Move myMove = minimaxHelper(boardState);

        // Return your move to be processed by the server.
        return myMove;
    }

    /**
     * Helper method that clones the current board state and passes 
     * it to the minimax algorithm to determine the best move from the 
     * current state. 
     * 
     * @param boardState
     * @return bestMove
     */
    public Move minimaxHelper(PentagoBoardState boardState) {
    	
    	// Make the ids globally available for the evaluate function
    	player = player_id;
    	opponent = boardState.getOpponent();
 
    	// To avoid illegal moves, consider only the legal moves from 
    	// this state
    	List<PentagoMove> options = boardState.getAllLegalMoves();
    	// Perform a random initial move
    	Random rand = new Random();
    	int doRandom = rand.nextInt(options.size());
    	PentagoMove bestMove = options.get(doRandom);
    	int bestValue = DEFAULT_ALPHA;
    	int value;
    	
    	// Evaluate move options
    	for (PentagoMove move : options) {
    		// Requires cloning, to keep the actual board intact
    		PentagoBoardState cloneState = (PentagoBoardState) boardState.clone();
    		// Process the move on the cloned board
    		cloneState.processMove(move);
    		// recursively call minimax, the max player is false initially
            value = abMinimax(cloneState, false, DEFAULT_ALPHA, DEFAULT_BETA, MAX_DEPTH-1);
            // update bestValue depending on the value returned by minimax
            if (value > bestValue) {
                bestMove = move;
                bestValue = value;
            }
    	}
    	
    	return bestMove;
    }

    /**
     * (Recursive) Alpha-Beta Pruning algorithm. 
     * Recursively calls itself, until it reaches the max depth. Updates the alpha on max player 
     * and beta for min player.  
     * 
     * @param boardState
     * @param alpha
     * @param beta
     * @param maxDepth
     * @param maxPlayer
     * @return bestValue
     */
    public int abMinimax(PentagoBoardState boardState, boolean maxPlayer, int alpha, int beta, int maxDepth) {
    	List<PentagoMove> legalOptions = boardState.getAllLegalMoves();
   
    	int bestValue = DEFAULT_ALPHA;
    	int value;
    	
    	// Base case
    	if (maxDepth == 0 || boardState.gameOver()){
    		int utilFunc = utility(boardState);
    		return utilFunc;
        }
    	
    	if (maxPlayer) {
    		bestValue = DEFAULT_ALPHA;
    		for (PentagoMove move : legalOptions) {
        		PentagoBoardState cloneState = (PentagoBoardState) boardState.clone();
        		cloneState.processMove(move);
        		value = abMinimax(cloneState, false, alpha, beta, maxDepth-1);
                bestValue = Math.max(bestValue, value);
                // For max player update alpha
                alpha = Math.max(bestValue, alpha);
                if (beta <= alpha) {
                	break;
                }
            }
    	}
    	
    	else {
    		bestValue = DEFAULT_BETA;
    		for (PentagoMove move : legalOptions) {
    			PentagoBoardState cloneState = (PentagoBoardState) boardState.clone();
        		cloneState.processMove(move);
        		value = abMinimax(cloneState, true, alpha, beta, maxDepth-1);
                bestValue = Math.min(bestValue, value);
                // For min player update beta
                beta = Math.min(bestValue, beta);
                if (beta <= alpha) {
                	break;
                }
            }
        }
    	return bestValue;
    }
    
    
    /**
     * Utility function for the minimax algorithm
     * 
     * @param boardState
     * @return score
     */
    public int utility(PentagoBoardState boardState) {
    	int score = 0;
 
        if (player == boardState.getWinner()) {
        	score += 1;
            return score;
        }
        else if (opponent == boardState.getWinner()) {
        	score -= 1 ;
            return score;
        }
        else if (boardState.gameOver()) {
            return score;
        }
       
        return score;
        
    }
}
