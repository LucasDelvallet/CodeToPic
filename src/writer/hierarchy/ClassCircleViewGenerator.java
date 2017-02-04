package writer.hierarchy;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import com.github.javaparser.ast.CompilationUnit;

import reader.ToBytes;
import util.RGB;
import writer.hierarchy.visitors.ClassLevelVisitor;

public class ClassCircleViewGenerator extends CircleViewGenerator<ClassLevelVisitor> implements IHierarchyView {

	public ClassCircleViewGenerator(ToBytes convertor, String targetFolder, CompilationUnit cu) {
		super(convertor, targetFolder, cu);
	}

	@Override
	public RGB getNameColor() {
		MessageDigest digest;
		int red = 0, green = 0, blue = 0;
		try {
			digest = MessageDigest.getInstance("SHA-256");
			byte[] hash = digest.digest(levelVisitor.getName().getBytes(StandardCharsets.UTF_8));
			red = hash[0];
			green = hash[15];
			blue = hash[31];
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		
		return new RGB(red, green, blue);
	}

	@Override
	public RGB getCodeColor() {
		int red, green;
		red = (int)((levelVisitor.getNumberOfVariableDeclaration() / (float)levelVisitor.getNumberOfLine()) * 255);
		green = (int)((levelVisitor.getNumberOfMethode() / (float) levelVisitor.getNumberOfLine()) * 255);
		return new RGB(red, green, 255);
	}

	@Override
	public int getNumberOfLines() {
		return levelVisitor.getNumberOfLine();
	}

	@Override
	public ClassLevelVisitor createLevelVisitor(CompilationUnit cu) {
		return new ClassLevelVisitor(cu);
	}

}
