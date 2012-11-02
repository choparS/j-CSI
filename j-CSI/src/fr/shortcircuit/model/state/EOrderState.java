package fr.shortcircuit.model.state;

public enum EOrderState
{
	//Declaration is inversed to allow inner referencing
	TRANSACTION_ENDED(	"Transaction ended", 	null),
	TRANSACTION_STARTED("Transaction started", 	TRANSACTION_ENDED),
	TRANSACTION_NULL(	"Transaction null", 	TRANSACTION_STARTED);
	
	/** Following step */
	private EOrderState 		nextStepState;
	
	/** State */
	private String 			state;
	
	
	
	private EOrderState(String state, EOrderState nextStepState)
	{
		this.state 			= state;
		this.nextStepState	= nextStepState;
	}

	public String getState()					{return state;}

	public EOrderState getNextStepState()		{return nextStepState;}
	
}
