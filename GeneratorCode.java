/*
     * Generator core
     */

    @Override
    public void getDescription(StringBuilder sb, int indent) {
	sb.append("Generates instances based on 5 different concept functions "
		+ "that describe whether another agent is "
		+ "interested or not in an item.");
    }

    @Override
    protected void prepareForUseImpl(TaskMonitor tm, ObjectRepository or) {
	
	classFunction = concepts[this.functionOption.getValue() - 1];

	FastVector attributes = new FastVector();
	attributes.addElement(new Attribute("color",
		Arrays.asList(colorValues)));
	attributes.addElement(new Attribute("price",
		Arrays.asList(priceValues)));
	attributes.addElement(new Attribute("payment",
		Arrays.asList(paymentValues)));
	attributes.addElement(new Attribute("amount",
		Arrays.asList(amountValues)));
	attributes.addElement(new Attribute("deliveryDelay",
		Arrays.asList(deliveryDelayValues)));

	this.instanceRandom = new Random(System.currentTimeMillis());

	FastVector classLabels = new FastVector();
	for (int i = 0; i < classValues.length; i++) {
	    classLabels.addElement(classValues[i]);
	}

	attributes.addElement(new Attribute("class", classLabels));
	this.streamHeader = new InstancesHeader(new Instances(
		getCLICreationString(InstanceStream.class), attributes, 0));
	this.streamHeader.setClassIndex(this.streamHeader.numAttributes() - 1);

	restart();
    }

    @Override
    public InstancesHeader getHeader() {
	return streamHeader;
    }

    @Override
    public long estimatedRemainingInstances() {
	return Integer.MAX_VALUE;
    }

    @Override
    public boolean hasMoreInstances() {
	return true;
    }

    @Override
    public InstanceExample nextInstance() {
	Instance instnc = null;

	boolean classFound = false;
	while (!classFound) {
	    //randomize indexes for new instance
	    int indexColor = this.instanceRandom.nextInt(colorValues.length);
	    int indexPrice = this.instanceRandom.nextInt(priceValues.length);
	    int indexPayment = this.instanceRandom.nextInt(paymentValues.length);
	    int indexAmount = this.instanceRandom.nextInt(amountValues.length);
	    int indexDelivery = this.instanceRandom.nextInt(deliveryDelayValues.length);
	    //retrieve values
	    String color = colorValues[indexColor];
	    String price = priceValues[indexPrice];
	    String payment = paymentValues[indexPayment];
	    String amount = amountValues[indexAmount];
	    String delivery = deliveryDelayValues[indexDelivery];
	    int classValue = classFunction.
		    determineClass(color, price, payment, amount, delivery);

	    instnc = new DenseInstance(streamHeader.numAttributes());
	    //set values
	    instnc.setDataset(this.getHeader());
	    instnc.setValue(0, Arrays.asList(colorValues).indexOf(color));
	    instnc.setValue(1, Arrays.asList(priceValues).indexOf(price));
	    instnc.setValue(2, Arrays.asList(paymentValues).indexOf(payment));
	    instnc.setValue(3, Arrays.asList(amountValues).indexOf(amount));
	    instnc.setValue(4, Arrays.asList(deliveryDelayValues).indexOf(delivery));

	    if (classValue == 0 && !nextClassShouldBeZero) {
		instnc = classFunction.makeTrue(instnc);
		classValue = 1;
		nextClassShouldBeZero = !nextClassShouldBeZero;
		classFound = true;
	    } else if (classValue == 0 && nextClassShouldBeZero) {
		nextClassShouldBeZero = !nextClassShouldBeZero;
		classFound = true;
	    } else if (classValue == 1 && !nextClassShouldBeZero) {
		nextClassShouldBeZero = !nextClassShouldBeZero;
		classFound = true;
	    }
	    instnc.setClassValue((int) classValue);
	    
	}
	//add noise
	int newClassValue = addNoise((int) instnc.classValue());
	instnc.setClassValue(newClassValue);
	return new InstanceExample(instnc);
    }

    @Override
    public boolean isRestartable() {
	return true;
    }

    @Override
    public void restart() {
	this.instanceRandom = new Random(this.instanceRandomSeedOption.getValue());
	this.nextClassShouldBeZero = false;
    }

    int addNoise(int classObtained) {
	if (this.instanceRandom.nextFloat() <= this.noisePercentage.getValue()) {
	    classObtained = classObtained == 0 ? 1 : 0;
	}
	return classObtained;
    }

    private static int indexOfValue(String value, Object[] arr) {
	int index = Arrays.asList(arr).indexOf(value);
	return index;
    }
}