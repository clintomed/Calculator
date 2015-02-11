package com.clintonmedbery.calculator;

import java.io.Serializable;
import java.text.NumberFormat;


public class CalculatorEngine implements Serializable
{
    private static final long serialVersionUID = -5587095393398443766L;

    private final int maxDisplayLength;
    private double accumulator;
    private double maxValue;
    private double minValue;
    private StringBuilder display;
    private Operation savedOp;
    private boolean opPerformed;
    private boolean hasErrors;


    /**
     * Construct a new calculator engine with the specified maximum display
     * length.  The display is initialized to "0".
     */
    public CalculatorEngine(int maxDisplayLength)
    {
        this.maxDisplayLength = maxDisplayLength;
        display = new StringBuilder(maxDisplayLength);

        // set max and min values
        StringBuilder buffer = new StringBuilder(maxDisplayLength);
        for (int n = 0;  n < maxDisplayLength;  ++n)
            buffer.append('9');

        maxValue = Double.parseDouble(buffer.toString());

        buffer.setCharAt(0, '-');
        minValue = Double.parseDouble(buffer.toString());

        clear();
    }


    /**
     * Default constructor.  Construct a new calculator engine with
     * a display length of 12.  The display is initialized to "0".
     */
    public CalculatorEngine()
    {
        this(12);
    }


    /**
     * Clear the calculator.  The accumulator is set to 0.0, and the
     * display is set to "0".
     */
    public void clear()
    {
        hasErrors = false;
        clearEntry();
        accumulator = 0.0;
        savedOp = null;
        opPerformed = false;
    }


    /**
     * Clear the display;  that is, set the display to "0".
     */
    public void clearEntry()
    {
        if (!hasErrors)
        {
            display.setLength(0);
            display.append('0');
        }
    }


    /**
     * Append a new character to the end of the display.  The new character
     * must be either a digit or a decimal point, and the number of characters
     * in the display must be less than the maximum display length;  otherwise
     * this method has no effect, and the input character is ignored.
     *
     * @param c  the character to be appended to the end of the display.
     */
    public void insert(char c)
    {
        if (!hasErrors && display.length() < maxDisplayLength)
        {
            if (opPerformed)
            {
                clearEntry();
                opPerformed = false;
            }

            if (Character.isDigit(c))
            {
                if (display.length() == 1 && display.charAt(0) == '0')
                    display.setLength(0);

                display.append(c);
            }
            else if ((c == '.') && (!display.toString().contains(".")))
                display.append(c);
        }
    }


    /**
     * Toggles the sign (+/-) of the display.
     */
    public void toggleSign()
    {
        if (!hasErrors)
        {
            char leadingChar = display.charAt(0);

            if (getDisplayValue() != 0.0)
            {
                if (leadingChar == '-')
                    display.deleteCharAt(0);
                else if (display.length() < maxDisplayLength)
                    display.insert(0, '-');
            }
        }
    }


    /**
     *  Returns a string representation of the display.
     */
    public String getDisplay()
    {
        return display.toString();
    }


    /**
     *  Returns a double representation of the display.
     */
    public double getDisplayValue()
    {
        if (!hasErrors)
            return Double.parseDouble(display.toString());
        else
            return Double.NaN;
    }


    /**
     * Perform the specified operation.
     */
    public void perform(Operation op)
    {
        if (!hasErrors && savedOp != null)
        {
            switch (savedOp)
            {
                case ADD:
                    accumulator += getDisplayValue();
                    break;
                case SUBTRACT:
                    accumulator -= getDisplayValue();
                    break;

                case MULTIPLY:
                    accumulator *= getDisplayValue();
                    break;

                case DIVIDE:
                    accumulator /= getDisplayValue();
                    break;
                default:
                    // do nothing
            }

            if (isInRange(accumulator))
            {
                setDisplay(accumulator);
                savedOp = null;
            }
            else
                error();
        }

        if (!hasErrors || op == Operation.CLEAR)
        {
            switch (op)
            {
                case ADD:
                case SUBTRACT:
                case MULTIPLY:
                case DIVIDE:
                    accumulator = getDisplayValue();
                    savedOp = op;
                    break;

                case EQUALS:
                    setDisplay(accumulator);
                    break;

                case CLEAR:
                    clear();
                    break;

                case CLEAR_ENTRY:
                    clearEntry();
                    break;
            }

            opPerformed = true;
        }
    }


    /**
     * Sets display to a string version of the specified value.
     */
    private void setDisplay(double value)
    {
        if (isInRange(value))
        {
            String valueStr = Double.toString(value);

            // if valueStr contains a decimal point, round value
            // to maxDisplayLength and remove trailing zeros
            int decimalPointIndex = valueStr.indexOf(".");
            if (decimalPointIndex > 0)
            {
                // round to maxDisplayLength
                if (valueStr.length() > maxDisplayLength)
                {
                    NumberFormat nf = NumberFormat.getInstance();
                    nf.setGroupingUsed(false);
                    nf.setMaximumFractionDigits(maxDisplayLength - decimalPointIndex - 1);
                    valueStr = nf.format(value);
                }
            }

            // if valueStr still contains a decimal point, check for
            // and remove ".0" as the last two characters
            decimalPointIndex = valueStr.indexOf(".");
            if (decimalPointIndex > 0 && valueStr.length() >= 3)
            {
                if (decimalPointIndex == valueStr.length() - 2
                        && valueStr.charAt(valueStr.length() -1) == '0')
                {
                    valueStr = valueStr.substring(0, valueStr.length() - 2);
                }
            }

            if (valueStr.length() > maxDisplayLength)
                error();
            else
            {
                display.setLength(0);
                display.append(valueStr);
            }
        }
        else
            error();
    }


    /**
     * Returns true if the specified value is within the allowable
     * range for this CalculatorEngine.
     */
    private boolean isInRange(double value)
    {
        return value <= maxValue && value >= minValue;
    }


    /**
     * Signal an error within this CalculatorEngine.
     */
    private void error()
    {
        clear();
        hasErrors = true;
        accumulator = Double.NaN;
        display.setLength(0);
        display.append("Error");
    }


    @Override
    public String toString()
    {
        return "CalculatorEngine [accumulator=" + accumulator + ", display=" + display
                + ", opPerformed=" + opPerformed + ", savedOp=" + savedOp + "]";
    }
}