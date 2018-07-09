package student.example;

public interface Movable
{
	//all abstract,public method
	enum Direction
	{
		DOWN,LEFT,RIGHT,SPACE
	};
	
	void fall();
	void move(Direction dir);

}
