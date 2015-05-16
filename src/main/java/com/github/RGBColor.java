package com.github;

/**
 * <p>
 * </p>
 * @author kisel.nikolay@gmail.com
 * @since May 16, 2015.
 */
public enum RGBColor
{
  WHITE( -1 ),
  BLACK( -16777216 );

  RGBColor( int value )
  {
    this.value = value;
  }

  private int value;

  public int getValue()
  {
    return value;
  }

  public static RGBColor getColor( int rgbIntValue )
  {
    for( RGBColor rgbColor : RGBColor.values() ) {
      if ( rgbIntValue > rgbColor.getValue() - 20
        && rgbIntValue < rgbColor.getValue() + 20 ) {
        return rgbColor;
      }
    }

    System.out.println( rgbIntValue );
    return null;
  }
}
