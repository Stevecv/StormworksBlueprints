
import java.awt.*;
import java.awt.image.*;

public class NoiseFilter {
    public BufferedImage performDenoise(BufferedImage image) {

        // TODO perform an average denoise may work for most images
        /*
         * the kernel applied is1/9|1/9|1/91/9|1/9|1/91/9|1/9|1/9
         *
         * this is a blur and a denoise in one so calling it again only causes a
         * blurier image
         */

        BufferedImage proxyimage=image;

        // the new image to be stored as a denoised image
        BufferedImage image2 = new BufferedImage(proxyimage.getWidth(),proxyimage.getHeight(), BufferedImage.TYPE_INT_RGB);

        // the current position properties
        int x = 0;
        int y = 0;

        // a neighbor pixel to add to the map

        // the image width and height properties
        int width = proxyimage.getWidth();
        int height = proxyimage.getHeight();

        // loop through pixels getting neighbors and resetting colors
        for (x = 1; x < width - 1; x++) {
            for (y = 1; y < height - 1; y++) {

                // get the neighbor pixels for the transform
                Color c00 = new Color(proxyimage.getRGB(x - 1, y - 1));
                Color c01 = new Color(proxyimage.getRGB(x - 1, y));
                Color c02 = new Color(proxyimage.getRGB(x - 1, y + 1));
                Color c10 = new Color(proxyimage.getRGB(x, y - 1));
                Color c11 = new Color(proxyimage.getRGB(x, y));
                Color c12 = new Color(proxyimage.getRGB(x, y + 1));
                Color c20 = new Color(proxyimage.getRGB(x + 1, y - 1));
                Color c21 = new Color(proxyimage.getRGB(x + 1, y));
                Color c22 = new Color(proxyimage.getRGB(x + 1, y + 1));

                // apply the kernel for r
                int r = c00.getRed() / 9 + c01.getRed() / 9 + c02.getRed() / 9
                        + c10.getRed() / 9 + c11.getRed() / 9 + c12.getRed()
                        / 9 + c20.getRed() / 9 + c21.getRed() / 9
                        + c22.getRed() / 9;

                // apply the kernel for g
                int g = c00.getGreen() / 9 + c01.getGreen() / 9
                        + c02.getGreen() / 9 + c10.getGreen() / 9
                        + c11.getGreen() / 9 + c12.getGreen() / 9
                        + c20.getGreen() / 9 + c21.getGreen() / 9
                        + c22.getGreen() / 9;

                // apply the transformation for b
                int b = c00.getBlue() / 9 + c01.getBlue() / 9 + c02.getBlue()
                        + c10.getBlue() / 9 + c11.getBlue() / 9 + c12.getBlue()
                        / 9 + c20.getBlue() / 9 + c21.getBlue() / 9
                        + c22.getBlue() / 9;

                // set the new rgb values
                r = Math.min(255, Math.max(0, r));
                g = Math.min(255, Math.max(0, g));
                b = Math.min(255, Math.max(0, b));

                Color c = new Color(r, g, b);

                image2.setRGB(x, y, c.getRGB());

            }
        }

        // reset the image
        return image;
    }


        public final static int IMPULSE = 0;
        public final static int GAUSSIAN = 1;
        protected int noiseType = IMPULSE;
        protected double stdDev = 10.0;
        protected double impulseRatio = 0.05;


        public NoiseFilter() {
        }


        public NoiseFilter(int noiseType) {
            setNoiseType(noiseType);
        }


        public NoiseFilter(int noiseType, double parameter) {
            setNoiseType(noiseType);

            if (noiseType == IMPULSE) setImpulseRatio(parameter);
            if (noiseType == GAUSSIAN) setGaussianStdDev(parameter);
        }


        public void setNoiseType(int noiseType) {
            this.noiseType = noiseType;
        }


        public int getNoiseType() {
            return noiseType;
        }


        public void setGaussianStdDev(double stdDev) {
            this.stdDev = stdDev;
        }


        public double getGaussianStdDev() {
            return stdDev;
        }

        public void setImpulseRatio(double impulseRatio) {
            this.impulseRatio = impulseRatio;
        }

        public double getImpulseRatio() {
            return impulseRatio;
        }


        protected BufferedImage impulseNoise(BufferedImage image, BufferedImage output) {
            output.setData(image.getData());

            Raster source = image.getRaster();
            WritableRaster out = output.getRaster();

            double rand;
            double halfImpulseRatio = impulseRatio / 2.0;
            int bands  = out.getNumBands();
            int width  = image.getWidth();  // width of the image
            int height = image.getHeight(); // height of the image
            java.util.Random randGen = new java.util.Random();

            for (int j=0; j<height; j++) {
                for (int i=0; i<width; i++) {
                    rand = randGen.nextDouble();
                    if (rand < halfImpulseRatio) {
                        for (int b=0; b<bands; b++) out.setSample(i, j, b, 0);
                    } else if (rand < impulseRatio) {
                        for (int b=0; b<bands; b++) out.setSample(i, j, b, 255);
                    }
                }
            }

            return output;
        }


        protected BufferedImage gaussianNoise(BufferedImage image) {
            Raster source = image.getRaster();
            BufferedImage output = image;
            WritableRaster out = output.getRaster();

            int currVal;                    // the current value
            double newVal;                  // the new "noisy" value
            double gaussian;                // gaussian number
            int bands = out.getNumBands(); // number of bands
            int width = image.getWidth();  // width of the image
            int height = image.getHeight(); // height of the image
            java.util.Random randGen = new java.util.Random();

            for (int j = 0; j < height; j++) {
                for (int i = 0; i < width; i++) {
                    gaussian = -randGen.nextGaussian();

                    for (int b = 0; b < bands; b++) {
                        newVal = stdDev * gaussian;
                        currVal = source.getSample(i, j, b);
                        newVal = newVal + currVal;
                        if (newVal < 0) newVal = 0.0;
                        if (newVal > 255) newVal = 255.0;

                        out.setSample(i, j, b, (int) (newVal));
                    }
                }
            }

            return output;
        }
}
