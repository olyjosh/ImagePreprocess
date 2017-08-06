package preprocessing.preprocesses;

/**
 * @author Joshua Aroke (olyjosh)
 * olyjoshone@gmail.com
 * f12softwares.com
 */
import ij.ImagePlus;
import ij.io.Opener;
import ij.process.ByteProcessor;

import java.awt.Image;
import java.awt.image.BufferedImage;

public class Skeletonize {
	public static BufferedImage skel(String args) {
		Image imgresult;
		ImagePlus imp = new Opener().openImage(args);

//				.openImage("../../datas2tests/toBeSkeletonize.tif");
		imp.setProcessor(imp.getTitle(), imp.getProcessor().convertToByte(true));
		ByteProcessor byteprocessor = (ByteProcessor) imp.getProcessor();
		byteprocessor.skeletonize();
		// the skeletonized image (should be updated)
		imgresult = imp.getImage();
		// The method above returns you a cached image
		// which is usually updated after editing the source processor,
		// but not always.
		// You can force it to update with:
		imp.updateAndDraw();
		imgresult = imp.getImage();
		// Or just get a new, fresh image directly:
		imgresult = byteprocessor.createImage();
                
		//new ImagePlus("", imgresult).show();
                return byteprocessor.getBufferedImage();
	}
        
        
        public static void main(String[] args) {
		Image imgresult;
		ImagePlus imp = new Opener().openImage("C:\\Users\\olyjosh\\Desktop\\Usman\\the rest\\1st 5\\1st 5\\akeem\\2.bmp");

//				.openImage("../../datas2tests/toBeSkeletonize.tif");
		imp.setProcessor(imp.getTitle(), imp.getProcessor().convertToByte(true));
		ByteProcessor byteprocessor = (ByteProcessor) imp.getProcessor();
		byteprocessor.skeletonize();
		// the skeletonized image (should be updated)
		imgresult = imp.getImage();
		// The method above returns you a cached image
		// which is usually updated after editing the source processor,
		// but not always.
		// You can force it to update with:
		imp.updateAndDraw();
		imgresult = imp.getImage();
		// Or just get a new, fresh image directly:
		imgresult = byteprocessor.createImage();
                
		new ImagePlus("", imgresult).show();
                //return byteprocessor.getBufferedImage();
	}
        
        
}