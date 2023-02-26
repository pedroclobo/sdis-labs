package pt.tecnico.ttt.server;

import io.grpc.stub.StreamObserver;
import pt.tecnico.ttt.*;

public class TTTServiceImpl extends TTTGrpc.TTTImplBase {

	/** Game implementation. */
	private TTTGame ttt = new TTTGame();

	@Override
	public synchronized void currentBoard(CurrentBoardRequest request, StreamObserver<CurrentBoardResponse> responseObserver) {
		// StreamObserver is used to represent the gRPC stream between the server and
		// client in order to send the appropriate responses (or errors, if any occur).

		CurrentBoardResponse response = CurrentBoardResponse.newBuilder().setBoard(ttt.toString()).build();

		// Send a single response through the stream.
		responseObserver.onNext(response);
		// Notify the client that the operation has been completed.
		responseObserver.onCompleted();
	}

	@Override
	public synchronized void play(PlayRequest request, StreamObserver<PlayResponse> responseObserver) {
		// Get the request parameters.
		int row = request.getRow();
		int column = request.getColumn();
		int player = request.getPlayer();

		PlayResponse response = PlayResponse.newBuilder().setResult(ttt.play(row, column, player)).build();
		responseObserver.onNext(response);

		// Notify the client that the operation has been completed.
		responseObserver.onCompleted();
	}

	@Override
	public synchronized void checkWinner(CheckWinnerRequest request, StreamObserver<CheckWinnerResponse> responseObserver) {
		CheckWinnerResponse response = CheckWinnerResponse.newBuilder().setWinner(ttt.checkWinner()).build();
		responseObserver.onNext(response);

		// Notify the client that the operation has been completed.
		responseObserver.onCompleted();
	}

}
