package writer.hierarchy;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import reader.ToBytes;
import util.RGB;
import writer.hierarchy.visitors.metrics.ClassMetric;

public class ClassCircleViewGenerator extends CircleViewGenerator implements IHierarchyView {
	private ClassMetric metric;

	public ClassCircleViewGenerator(ToBytes convertor, String targetFolder, ClassMetric metric) {
		super(convertor, targetFolder);
		this.metric = metric;
	}

	@Override
	public RGB getStrokeColor() {
		MessageDigest digest;
		int red = 0, green = 0, blue = 0;
		try {
			digest = MessageDigest.getInstance("SHA-256");
			
			if(metric.getName() == null) {
				return new RGB(0,0,0);
			}
			
			byte[] hash = digest.digest(metric.getName().getBytes(StandardCharsets.UTF_8));
			red = hash[0];
			green = hash[15];
			blue = hash[31];
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		
		return new RGB(red, green, blue);
	}

	@Override
	public RGB getBackgroundColor() {
		int red, blue;
		red = (int)((metric.getNumberOfVariableDeclaration() / (float)metric.getNumberOfLine()) * 255);
		blue = (int)((metric.getNumberOfMethode() / (float) metric.getNumberOfLine()) * 255);
		return new RGB(red, 255, blue);
	}

	@Override
	public int getNumberOfLines() {
		int childrenSize = 0;
		for(IHierarchyView child : children) {
			childrenSize += child.getNumberOfLines();
		}
		return metric.getNumberOfLine() - childrenSize;
	}

	@Override
	public String getName() {
		return metric.getName();
	}

}
