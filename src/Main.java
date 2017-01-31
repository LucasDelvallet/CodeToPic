import java.io.IOException;

import reader.FileToBytes;
import reader.SVGToBytes;
import reader.ToBytes;
import writer.FileGenerator;
import writer.Generator;
import writer.Generator.Extension;
import writer.SVGGenerator;
import writer.hierarchy.CircleViewGenerator;
import writer.hierarchy.IHierarchyView;
import writer.hierarchy.MethodeCircleViewGenerator;
import writer.hierarchy.ProjectCircleViewGenerator;

public class Main {

	public static void main(String[] args) {
		//ToBytes coder = new FileToBytes("ressources/test.txt");
		ToBytes coder = new FileToBytes("ressources/test.txt");
		//Generator generator = new ImageGenerator(coder, "ressources/generated/image/");
		CircleViewGenerator generator = new ProjectCircleViewGenerator(coder, "ressources/generated/image/");
		CircleViewGenerator generator2 = new MethodeCircleViewGenerator(coder, "ressources/generated/image/");
		generator.addChild(generator2);
		generator2 = new MethodeCircleViewGenerator(coder, "ressources/generated/image/");
		generator.addChild(generator2);
		try {
			generator.generate(Extension.BMP);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
//		//ImageRGBToBytes convertor = new ImageRGBToBytes("ressources/generated/image/test.bmp");
//		SVGToBytes convertor = new SVGToBytes("ressources/generated/image/test.svg");
//		generator = new FileGenerator(convertor, "ressources/generated/code/");
//		try {
//			generator.generate(Extension.TXT);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
	}

}
