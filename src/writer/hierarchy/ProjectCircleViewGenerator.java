package writer.hierarchy;

import com.github.javaparser.ast.CompilationUnit;

import reader.ToBytes;
import util.RGB;
import writer.hierarchy.visitors.LevelVisitor;

public class ProjectCircleViewGenerator extends CircleViewGenerator {

	public ProjectCircleViewGenerator(ToBytes convertor, String targetFolder) {
		super(convertor, targetFolder);
	}
	
	@Override
	public RGB getNameColor() {
		return new RGB(255, 0, 0);
	}
	
	@Override
	public RGB getCodeColor() {
		return new RGB(0, 255, 0);
	}

	@Override
	public int getNumberOfLines() {
		return 0;
	}

}
