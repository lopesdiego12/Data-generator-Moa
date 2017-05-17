/*
     * Labeling functions
     */
    protected interface ClassFunction {

	public int determineClass(String carid,
	String age,
	String avgvelocity,
	String gender,
	String crash,
	String km,
	String marcha,
	String road);

	public Instance makeTrue(Instance intnc);
    }

    protected static ClassFunction concepts[] = {
	new ClassFunction() {
	    Random r = new Random(Integer.MAX_VALUE);

	    @Override
	    public int determineClass(String carid,
				String age,
				String avgvelocity,
				String gender,
				String crash,
				String km,
				String marcha,
				String road) {
			if (Integer.parseInt(avgvelocityValues) > 20 || Integer.parseInt(avgvelocityValues) < 90 &&
			crashValues.equals("N") && roadValues.equals("N") ){
				return indexOfValue("Perfect", classValues);
			}
			else if (Integer.parseInt(avgvelocityValues) > 20 || Integer.parseInt(avgvelocityValues) < 90 &&
			crashValues.equals("N") && roadValues.equals("Y") ){
				return indexOfValue("Perfect", classValues);
			}
			else if (Integer.parseInt(avgvelocityValues) > 20 || Integer.parseInt(avgvelocityValues) < 90 &&
			crashValues.equals("Y") && roadValues.equals("N") ){
				return indexOfValue("Regular", classValues);
			}
			else if (Integer.parseInt(avgvelocityValues) > 20 || Integer.parseInt(avgvelocityValues) < 90 &&
			crashValues.equals("Y") && roadValues.equals("Y") ){
				return indexOfValue("Poor", classValues);
			}
			else if (Integer.parseInt(avgvelocityValues) < 20 || Integer.parseInt(avgvelocityValues) > 90 &&
			crashValues.equals("N") && roadValues.equals("N") ){
				return indexOfValue("Perfect", classValues);
			}
			else if (Integer.parseInt(avgvelocityValues) < 20 || Integer.parseInt(avgvelocityValues) > 90 &&
			crashValues.equals("N") && roadValues.equals("Y") ){
				return indexOfValue("Regular", classValues);
			}
			else if (Integer.parseInt(avgvelocityValues) < 20 || Integer.parseInt(avgvelocityValues) > 90 &&
			crashValues.equals("Y") && roadValues.equals("N") ){
				return indexOfValue("Poor", classValues);
			}
			return indexOfValue("Poor", classValues);
			}
			
	    @Override
	    public Instance makeTrue(Instance intnc) {
		int part = r.nextInt(2);
		if (part == 0) {

		    intnc.setValue(1, indexOfValue("normal", priceValues));
		    intnc.setValue(3, indexOfValue("high", amountValues));
		} else {
		    intnc.setValue(0, indexOfValue("brown", colorValues));
		    intnc.setValue(1, indexOfValue("veryLow", priceValues));
		    intnc.setValue(4, indexOfValue("high", deliveryDelayValues));
		}
		intnc.setClassValue(indexOfValue("interested", classValues));
		return intnc;
	    }

	},
	new ClassFunction() {
	    Random r = new Random(Integer.MAX_VALUE);

	    @Override
	    public int determineClass(String color,
		    String price,
		    String payment,
		    String amount,
		    String deliveryDelay) {
		if (price.equals("high") && amount.equals("veryHigh")
			&& deliveryDelay.equals("high")) {
		    return indexOfValue("interested", classValues);
		}
		return indexOfValue("notInterested", classValues);
	    }

	    @Override
	    public Instance makeTrue(Instance intnc) {
		intnc.setValue(1, indexOfValue("high", priceValues));
		intnc.setValue(3, indexOfValue("veryHigh", amountValues));
		intnc.setValue(4, indexOfValue("high", deliveryDelayValues));
		intnc.setClassValue(Arrays.asList(classValues).indexOf("interested"));
		return intnc;
	    }	    
	},
	new ClassFunction() {
	    Random r = new Random(Integer.MAX_VALUE);

	    @Override
	    public int determineClass(String color,
		    String price,
		    String payment,
		    String amount,
		    String deliveryDelay) {
		if ((price.equals("veryLow")
			&& payment.equals("0") && amount.equals("high"))
			|| (color.equals("red") && price.equals("low")
			&& payment.equals("30"))) {
		    return indexOfValue("interested", classValues);
		}
		return indexOfValue("notInterested", classValues);
	    }

	    @Override
	    public Instance makeTrue(Instance intnc) {
		int part = r.nextInt(2);
		if (part == 0) {
		    intnc.setValue(1, indexOfValue("veryLow", priceValues));
		    intnc.setValue(2, indexOfValue("0", paymentValues));
		    intnc.setValue(3, indexOfValue("high", amountValues));
		} else {
		    intnc.setValue(0, indexOfValue("red", colorValues));
		    intnc.setValue(1, indexOfValue("low", priceValues));
		    intnc.setValue(2, indexOfValue("30", paymentValues));
		}
		intnc.setClassValue(Arrays.asList(classValues).indexOf("interested"));
		return intnc;
	    }	    
	},
	new ClassFunction() {
	    Random r = new Random(Integer.MAX_VALUE);

	    @Override
	    public int determineClass(String color,
		    String price,
		    String payment,
		    String amount,
		    String deliveryDelay) {
		if ((color.equals("black")
			&& payment.equals("90")
			&& deliveryDelay.equals("veryLow"))
			|| (color.equals("magenta")
			&& price.equals("high")
			&& deliveryDelay.equals("veryLow"))) {
		    return indexOfValue("interested", classValues);
		}
		return indexOfValue("notInterested", classValues);
	    }

	    @Override
	    public Instance makeTrue(Instance intnc) {
		int part = r.nextInt(2);
		if (part == 0) {
		    intnc.setValue(0, indexOfValue("black", colorValues));
		    intnc.setValue(2, indexOfValue("90", paymentValues));
		    intnc.setValue(4, indexOfValue("veryLow", deliveryDelayValues));
		} else {
		    intnc.setValue(0, indexOfValue("magenta", colorValues));
		    intnc.setValue(1, indexOfValue("high", priceValues));
		    intnc.setValue(4, indexOfValue("veryLow", deliveryDelayValues));
		}
		intnc.setClassValue(Arrays.asList(classValues).indexOf("interested"));
		return intnc;
	    }	    
	},
	new ClassFunction() {
	    Random r = new Random(Integer.MAX_VALUE);

	    @Override
	    public int determineClass(String color,
		    String price,
		    String payment,
		    String amount,
		    String deliveryDelay) {
		if ((color.equals("blue")
			&& payment.equals("60")
			&& amount.equals("low")
			&& deliveryDelay.equals("normal"))
			|| (color.equals("cyan")
			&& amount.equals("low")
			&& deliveryDelay.equals("normal"))) {
		    return indexOfValue("interested", classValues);
		}
		return indexOfValue("notInterested", classValues);
	    }

	    @Override
	    public Instance makeTrue(Instance intnc) {
		int part = r.nextInt(2);
		if (part == 0) {
		    intnc.setValue(0, indexOfValue("blue", colorValues));
		    intnc.setValue(2, indexOfValue("60", paymentValues));
		    intnc.setValue(3, indexOfValue("low", amountValues));
		    intnc.setValue(4, indexOfValue("normal", deliveryDelayValues));
		} else {
		    intnc.setValue(0, indexOfValue("cyan", colorValues));
		    intnc.setValue(3, indexOfValue("low", amountValues));
		    intnc.setValue(4, indexOfValue("normal", deliveryDelayValues));
		}
		intnc.setClassValue(Arrays.asList(classValues).indexOf("interested"));
		return intnc;
	    }	    
	}
    };