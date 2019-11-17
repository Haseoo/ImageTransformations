package com.github.haseoo.gkproject.imagetransformations.utils;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.val;

import static com.github.haseoo.gkproject.imagetransformations.utils.JavaFXUtils.createImage;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ImageResize {
    public static Image simpleResize(Image source, Pair<Integer> scale) {
        Pair<Integer> newDimensions = calculateNewDimensions(source, scale.getX(), scale.getY());
        Pair<Double> newScale = calculateNewScale(new Pair<>(source.getWidth(), source.getHeight()), newDimensions);
        return createImage(newDimensions,
                point -> source.getPixelReader().getColor((int) (point.getX() * newScale.getX()),
                        (int) (point.getY() * newScale.getY())));
    }

    public static Image bilinearResize(Image sourceImage, Pair<Integer> scale) {
        Pair<Integer> newDimensions = calculateNewDimensions(sourceImage, scale.getWidth(), scale.getHeight());
        Pair<Integer> oldDimensions = new Pair<>((int) sourceImage.getWidth(), (int) sourceImage.getHeight());
        val source = sourceImage.getPixelReader();
        return createImage(newDimensions, point -> {
            double ratioX = (oldDimensions.getWidth()*1.0)/(newDimensions.getWidth()*1.0);
            double ratioY = (oldDimensions.getHeight()*1.0)/(newDimensions.getHeight()*1.0);
            double x,y,dx,dy;

            int firstX,firstY,secondX, secondY;

            double red1,red2,red3,red4,red5,
                    green1,green2,green3,green4,green5,
                    blue1,blue2, blue3,blue4,blue5;

            x = (point.getX()*ratioX);
            y = (point.getY()*ratioY);
            dx  = point.getX()*ratioX - (int)(point.getX()*ratioX);
            dy = point.getY()*ratioY - (int)(point.getY()*ratioY);
            firstX = (int)x;
            firstY = (int)y;
            if(firstX + 1 >= oldDimensions.getX()){
                secondX = firstX;
            }else{
                secondX = firstX + 1;
            }
            if(firstY+1 >= oldDimensions.getY()){
                secondY = firstY;
            }else{
                secondY = firstY+1;
            }
            if(source.getColor(firstX,firstY).getOpacity() == 0){
                return Color.WHITE;
            }
//                kolory pixeli sasiadujacych gornych
            red1 = source.getColor(firstX,firstY).getRed();
            red2 = source.getColor(secondX,firstY).getRed();
            green1 = source.getColor(firstX,firstY).getGreen();
            green2 = source.getColor(secondX,firstY).getGreen();
            blue1 = source.getColor(firstX,firstY).getBlue();
            blue2 = source.getColor(secondX,firstY).getBlue();
//                interpolacja pozioma dla gory
            red3 = ((red1*(1-dx))+(red2*dx));
            green3 = ((green1*(1-dx))+(green2*dx));
            blue3 = ((blue1*(1-dx))+(blue2*dx));

//                kolory pixeli sasiadujacych dolnych
            red1 = source.getColor(firstX,secondY).getRed();
            red2 = source.getColor(secondX,secondY).getRed();
            green1 =  source.getColor(firstX,secondY).getGreen();
            green2 =  source.getColor(secondX,secondY).getGreen();
            blue1 =  source.getColor(firstX,secondY).getBlue();
            blue2 =  source.getColor(secondX,secondY).getBlue();
//                interpolacja pozioma dla dolu
            red4 = ((red1*(1-dx))+(red2*dx));
            green4 = ((green1*(1-dx))+(green2*dx));
            blue4 = ((blue1*(1-dx))+(blue2*dx));

//                interpolacja pionowa
            red5 = ((red3*(1-dy))+(red4*dy));
            green5 = ((green3*(1-dy))+(green4*dy));
            blue5 = ((blue3*(1-dy))+(blue4*dy));
            return Color.color(red5,green5,blue5);
        });
    }

    private static Pair<Integer> calculateNewDimensions(Image source, double scaleX, double scaleY) {
        return new Pair<>((int) (source.getWidth() * scaleX / 100.0),
                (int) (source.getHeight() * scaleY / 100.0));
    }

    private static Pair<Double> calculateNewScale(Pair<Double> oldDimensions, Pair<Integer> newDimensions) {
        return new Pair<>(oldDimensions.getWidth() / (double) newDimensions.getWidth(),
                oldDimensions.getHeight() / (double) newDimensions.getHeight());
    }

    private static Pair<Integer> getSecondPoint(Pair<Integer> oldDimensions, Pair<Integer> first) {
        int secondX;
        int secondY;
        if (first.getX() + 1 >= oldDimensions.getWidth()) {
            secondX = first.getX();
        } else {
            secondX = first.getX() + 1;
        }
        if (first.getY() + 1 >= oldDimensions.getHeight()) {
            secondY = first.getY();
        } else {
            secondY = first.getY() + 1;
        }
        return new Pair<>(secondX, secondY);
    }
}
