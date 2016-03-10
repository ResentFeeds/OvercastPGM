package overcast.pgm.parser;

public interface Parser<O, I> {
	
	ParserResult<O> parse(I input);
}