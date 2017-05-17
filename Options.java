  /*
     * OPTIONS
     */        
    public IntOption functionOption = new IntOption("function", 'f',
	    "Classification function used, as defined in the original paper.",
	    1, 1, 5);
    public FloatOption noisePercentage = new FloatOption("noise", 'n',
	    "% of class noise.", 0.05, 0.0, 1.0f);

    public IntOption instanceRandomSeedOption = new IntOption(
	    "instanceRandomSeed", 'i',
	    "Seed for random generation of instances.", 1);