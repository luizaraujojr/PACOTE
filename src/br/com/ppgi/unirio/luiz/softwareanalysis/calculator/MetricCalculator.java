package br.com.ppgi.unirio.luiz.softwareanalysis.calculator;

import java.util.Arrays;
import java.util.Vector;

import br.com.ppgi.unirio.luiz.softwareanalysis.model.Dependency;
import br.com.ppgi.unirio.luiz.softwareanalysis.model.DependencyType;
import br.com.ppgi.unirio.luiz.softwareanalysis.model.Project;
import br.com.ppgi.unirio.luiz.softwareanalysis.model.ProjectClass;

/**
 * DEFINIC�ES:
 * 
 * - acoplamento = n�mero de depend�ncias que as classes de um pacote possuem com classes de fora
 *   do pacote. Deve ser minimizado.
 *   
 * - coes�o = n�mero de depend�ncias que as classes de um pacote possuem com outras classes do
 *   mesmo pacote. Deve ser maximizado (ou seja, minimizamos seu valor com sinal invertido)
 *   
 * - spread = partindo de zero e percorrendo cada pacote, acumula o quadrado da diferen�a entre
 *   o n�mero de classes do pacote e o n�mero de classes do menor pacote
 *   
 * - diferenca = diferen�a entre o n�mero m�ximo de classes em um pacote e o n�mero m�nimo de
 *   classes em um pacote
 * 
 * @author Marcio Barros
 */
public class MetricCalculator
{
	private int classCount;
	private int packageCount;

	private int[][] dependencies;
	private int[] originalPackage;
	private int[] newPackage;
	private int[] originalClasses;
	private int[] newClasses;

	private int minClasses;
	private int maxClasses;
	private double[] classProbability;
	
	private double sum;
	private double sumSquare;
	private int count;
	private Project project;

	/**
	 * Inicializa o calculador de acoplamento
	 */
	public MetricCalculator(Project project) throws Exception
	{
		this.project = project;
		this.classCount = project.getClassCount();
		this.packageCount = project.getPackageCount();
		this.minClasses = this.maxClasses = 0;
		this.classProbability = null;
		prepareClasses(project);
	}
	
	/**
	 * Prepara as classes para serem processadas pelo programa
	 */
	public void prepareClasses(Project project) throws Exception
	{
		this.dependencies = new int[classCount][classCount];
		
		this.originalPackage = new int[classCount];
		this.newPackage = new int[classCount];
		
		this.originalClasses = new int[packageCount];
		this.newClasses = new int[packageCount];

		for (int i = 0; i < classCount; i++)
		{
			ProjectClass _class = project.getClassIndex(i);
			int sourcePackageIndex = project.getIndexForPackage(_class.getPackage());
			
			this.originalPackage[i] = sourcePackageIndex;
			this.newPackage[i] = sourcePackageIndex;
			this.originalClasses[sourcePackageIndex]++;
			this.newClasses[sourcePackageIndex]++; 

			for (int j = 0; j < _class.getDependencyCount(); j++)
			{
				String targetName = _class.getDependencyIndex(j).getElementName();
				int classIndex = project.getClassIndex(targetName);
				
				if (classIndex == -1)
//					throw new Exception ("Class not registered in project: " + targetName);
					System.out.println("Class not registered in project: " + targetName);
				else {dependencies[i][classIndex]++;}
			}
		}
	}

	/**
	 * Estima as probabilidades de distribui��o de classes 
	 */
	public void setClassDistributionProbabilities(int min, int expected, int max)
	{
		// guarda os par�metros de distribui��o de classes
		this.minClasses = min;
		this.maxClasses = max;
		this.classProbability = new double[max-min];
		
		// calcula a altura da distribui��o triangular
		double height = 2.0 / (max - min); 
		
		// calcula a equa��o da reta da esquerda
		double al = height / (expected - min);
		double bl = -min * al;
		
		// calcula a equa��o da reta da direita
		double ar = -height / (max - expected);
		double br = -max * ar;
		
		// estima as probabilidades
		for (int i = min; i < expected; i++)
			this.classProbability[i-min] = al / 2 * (i + 1) * (i + 1) + bl * (i + 1) - al / 2 * i * i - bl * i;
		
		for (int i = expected; i < max; i++)
			this.classProbability[i-min] = ar / 2 * (i + 1) * (i + 1) + br * (i + 1) - ar / 2 * i * i - br * i;
	}

	/**
	 * Estima as probabilidades de distribui��o de classes 
	 */
	private double calculateClassCountProbabilities(int count)
	{
		if (count < this.minClasses || count >= this.maxClasses)
			return 0.0;
		else
			return classProbability[count - this.minClasses]; 
	}

	/**
	 * Inicializa o processo de c�lculo
	 */
	public void reset()
	{
		for (int i = 0; i < classCount; i++)
			newPackage[i] = originalPackage[i];

		for (int i = 0; i < packageCount; i++)
			newClasses[i] = originalClasses[i];
	}

	/**
	 * Move uma classe para um pacote
	 */
	public void moveClass(int classIndex, int packageIndex)
	{
		int actualPackage = newPackage[classIndex];
		
		if (actualPackage != packageIndex)
		{
			newClasses[actualPackage]--;
			newPackage[classIndex] = packageIndex;
			newClasses[packageIndex]++;
		}
	}
	
	/**
	 * Returns the package of a given class
	 */
	public int getPackage(int classIndex)
	{
		return newPackage[classIndex];
	}

	/**
	 * Retorna o n�mero de classes de um pacote
	 */
	public int getClassCount(int packageIndex)
	{
		return newClasses[packageIndex];
	}

	/**
	 * Retorna o n�mero de movimentos de classes
	 */
	public int getMinimumClassCount()
	{
		int min = Integer.MAX_VALUE;

		for (int i = 0; i < packageCount; i++)
		{
			int count = newClasses[i];

			if (count < min)
				min = count;
		}

		return min;
	}

	/**
	 * Retorna o n�mero de movimentos de classes
	 */
	public int getMaximumClassCount()
	{
		int max = Integer.MIN_VALUE;

		for (int i = 0; i < packageCount; i++)
		{
			int count = newClasses[i];

			if (count > max)
				max = count;
		}

		return max;
	}

	/**
	 * Retorna o n�mero de pacotes com mais de uma classe
	 */
	public int getPackageCount()
	{
		int packages = 0;
		
		for (int i = 0; i < packageCount; i++)
			if (newClasses[i] > 0)
				packages++;

		return packages;
	}

	/**
	 * Calcula o numero de depend�ncias com origem em um dado pacote e t�rmino em outro de um pacote
	 */
	public int countOutboundEdges(int packageIndex)
	{
		int edges = 0;

		for (int i = 0; i < classCount; i++)
		{
			int currentPackage = newPackage[i];
			
			if (currentPackage != packageIndex)
				continue;
			
			for (int j = 0; j < classCount; j++)
				if (dependencies[i][j] > 0 && newPackage[j] != currentPackage)
					edges++;
		}

		return edges;
	}

	/**
	 * 
	 */
	public int calculateEfferentCoupling(int packageIndex)
	{
		boolean[] efferentClasses = new boolean[classCount];

		for (int i = 0; i < classCount; i++)
			efferentClasses[i] = false;
		
		for (int i = 0; i < classCount; i++)
		{
			int currentPackage = newPackage[i];
			
			if (currentPackage != packageIndex)
				continue;
			
			for (int j = 0; j < classCount; j++)
				if (dependencies[i][j] > 0 && newPackage[j] != currentPackage)
					efferentClasses[j] = true;
		}

		int count = 0;
		
		for (int i = 0; i < classCount; i++)
			if (efferentClasses[i])
				count++;

		return count;
	}

	/**
	 * 
	 */
	public double calculateEfferentCoupling()
	{
		double sum = 0.0;
		int count = 0;
		
		for (int i = 0; i < packageCount; i++)
			if (getClassCount(i) > 0)
			{
				sum += calculateEfferentCoupling(i);
				count++;
			}
		
		return sum / count;
	}

	/**
	 * Calcula o numero de depend�ncias com um pacote e t�rmino em um dado pacote
	 */
	public int countInboundEdges(int packageIndex)
	{
		int edges = 0;

		for (int i = 0; i < classCount; i++)
		{
			int currentPackage = newPackage[i];
			
			if (currentPackage == packageIndex)
				continue;
			
			for (int j = 0; j < classCount; j++)
				if (dependencies[i][j] > 0 && newPackage[j] == packageIndex)
					edges++;
		}

		return edges;
	}

	/**
	 * Calcula o numero de depend�ncias de classes externas a um pacote que dependem dele
	 */
	public int calculateAfferentCoupling(int packageIndex)
	{
		int afferentClasses = 0;

		for (int i = 0; i < classCount; i++)
		{
			int currentPackage = newPackage[i];
			
			if (currentPackage == packageIndex)
				continue;
			
			boolean dependsOnPackage = false;
			
			for (int j = 0; j < classCount && !dependsOnPackage; j++)
				if (dependencies[i][j] > 0 && newPackage[j] == packageIndex)
					dependsOnPackage = true;
			
			if (dependsOnPackage)
				afferentClasses++;
		}

		return afferentClasses;
	}

	/**
	 * Calcula o numero de depend�ncias de classes externas a um pacote que dependem dele
	 */
	public double calculateAfferentCoupling()
	{
		double sum = 0.0;
		int count = 0;
		
		for (int i = 0; i < packageCount; i++)
			if (getClassCount(i) > 0)
			{
				sum += calculateAfferentCoupling(i);
				count++;
			}
		
		return sum / count;
	}
	
	/**
	 * Calcula o n�mero de depend�ncias internas de um pacote
	 */
	public int countIntraEdges(int packageIndex)
	{
		int edges = 0;

		for (int i = 0; i < classCount; i++)
		{
			int currentPackage = newPackage[i];
			
			if (currentPackage != packageIndex)
				continue;
			
			for (int j = 0; j < classCount; j++)
				if (dependencies[i][j] > 0 && newPackage[j] == currentPackage)
					edges++;
		}

		return edges;
	}

	/**
	 * Retorna a dispers�o da distribui��o de classes em pacotes
	 */
	public int calculateDifference()
	{
		int min = getMinimumClassCount();
		int max = getMaximumClassCount();
		return max - min;
	}

	/**
	 * Retorna a dispers�o da distribui��o de classes em pacotes
	 */
	public double calculateSpread()
	{
		int min = getMinimumClassCount();
		double spread = 0.0;

		for (int i = 0; i < packageCount; i++)
		{
			int count = newClasses[i];
			spread += Math.pow(count - (double)min, 2);
		}

		return spread;
	}

	/**
	 * Calcula o acoplamento do projeto
	 */
	public int calculateCoupling()
	{
		int coupling = 0;

		for (int i = 0; i < classCount; i++)
		{
			int currentPackage = newPackage[i];
			
			for (int j = 0; j < classCount; j++)
				if (dependencies[i][j] > 0 && newPackage[j] != currentPackage)
					coupling += 2;
		}

		return coupling;
	}

	/**
	 * Calcula a coes�o do projeto
	 */
	public int calculateCohesion()
	{
		int cohesion = 0;

		for (int i = 0; i < classCount; i++)
		{
			int currentPackage = newPackage[i];
			
			for (int j = 0; j < classCount; j++)
				if (dependencies[i][j] > 0 && newPackage[j] == currentPackage)
					cohesion++;
		}

		return cohesion;
	}

	/**
	 * Calcula o coeficiente de modularidade do projeto
	 */
	public double calculateModularizationFactor(int packageIndex)
	{
		int[] inboundEdges = new int[packageCount];
		int[] outboundEdges = new int[packageCount];
		int[] intraEdges = new int[packageCount];

		for (int i = 0; i < classCount; i++)
		{
			int sourcePackage = newPackage[i];
			
			for (int j = 0; j < classCount; j++)
			{
				if (dependencies[i][j] > 0)
				{
					int targetPackage = newPackage[j];
					
					if (targetPackage != sourcePackage)
					{
						outboundEdges[sourcePackage]++;
						inboundEdges[targetPackage]++;
					}
					else
						intraEdges[sourcePackage]++;
				}
			}
		}
		
		int inter = inboundEdges[packageIndex] + outboundEdges[packageIndex];
		int intra = intraEdges[packageIndex];
		
		if (intra != 0 && inter != 0)
			return intra / (intra + 0.5 * inter);

		return 0.0;
	}

	/**
	 * Calcula o coeficiente de modularidade do projeto
	 */
	public double calculateModularizationQuality()
	{
		int[] inboundEdges = new int[packageCount];
		int[] outboundEdges = new int[packageCount];
		int[] intraEdges = new int[packageCount];

		for (int i = 0; i < classCount; i++)
		{
			int sourcePackage = newPackage[i];
			
			for (int j = 0; j < classCount; j++)
			{
				if (dependencies[i][j] > 0)
				{
					int targetPackage = newPackage[j];
					
					if (targetPackage != sourcePackage)
					{
						outboundEdges[sourcePackage]++;
						inboundEdges[targetPackage]++;
					}
					else
						intraEdges[sourcePackage]++;
				}
			}
		}
		
		double mq = 0.0;

		for (int i = 0; i < packageCount; i++)
		{
			int inter = inboundEdges[i] + outboundEdges[i];
			int intra = intraEdges[i];
			
			if (intra != 0 && inter != 0)
			{
				double mf = intra / (intra + 0.5 * inter);
				mq += mf;
			}
		}

		return mq;
	}

	/**
	 * Calcula o cluster score de um pacote
	 */
	public int calculateClusterScore(int packageIndex)
	{
		int score = 0;

		for (int i = 0; i < classCount-1; i++)
		{
			int sourcePackage = newPackage[i];

			if (sourcePackage == packageIndex)
			{
				for (int j = i+1; j < classCount; j++)
				{
					int targetPackage = newPackage[j];
	
					if (targetPackage == sourcePackage)
						if (dependencies[i][j] > 0 || dependencies[j][i] > 0) 
							score++;
						else
							score--; 
				}
			}
		}

		return score;
	}

	/**
	 * Calcula o EVM do projeto
	 */
	public int calculateEVM()
	{
		int evm = 0;

		for (int i = 0; i < classCount-1; i++)
		{
			int sourcePackage = newPackage[i];
			
			for (int j = i+1; j < classCount; j++)
			{
				int targetPackage = newPackage[j];

				if (targetPackage == sourcePackage)
					if (dependencies[i][j] > 0 || dependencies[j][i] > 0) 
						evm++;
					else
						evm--; 
			}
		}

		return evm;
	}

	/**
	 * Calcula o fator de distribui��o de classes 
	 */
	public double calculateClassDistributionFactor()
	{
		double cdf = 0.0;

		for (int i = 0; i < packageCount; i++)
		{
			int count = getClassCount(i);
			cdf += calculateClassCountProbabilities(count);
		}

		return cdf;
	}
	
	/**
	 * Calcula o desvio padr�o do n�mero de dependencias internas de um pacote
	 */
	public double calculateCohesionElegance()
	{		
		sum=0;
		sumSquare=0;
		count=0;

		for (int i = 0; i < packageCount; i++)
			add(countInboundEdges(i));		

		return standardDeviation();
	}
	
	/**
	 * calcula o desvio padr�o do n�mero de depend�ncias externas de cada pacote 
	 */
	public double calculateCouplingElegance()
	{
		sum=0;
		sumSquare=0;
		count=0;

		for (int i = 0; i < packageCount; i++)
			add(countOutboundEdges(i));

		return standardDeviation();
	}
		
	/**
	 * Calcula o desvio padr�o do n�mero de classes por pacote
	 */
	public double calculateClassElegance()
	{
		sum=0;
		sumSquare=0;
		count=0;
		
		for (int i = 0; i < packageCount; i++)
			add(getClassCount(i));

		return standardDeviation();
	}

	/**
	 * Retorna as classes que pertencem a um pacote, dado seu �ndice
	 */
	private int[] getPackageClasses(int packageIndex)
	{
		int classesOnPackage = getClassCount(packageIndex);
		int[] _classes = new int[classesOnPackage];
		int walker = 0;
		
		for (int i = 0; i < classCount; i++)
		{
			int currentPackage = newPackage[i];
			
			if (currentPackage == packageIndex)
				_classes[walker++] = i;
		}

		return _classes;
	}
	
	/**
	 * Calcula o LCOM5 de um pacote, dado seu �ndice
	 */
	public double calculateLCOM5(int packageIndex)
	{
		int classesOnPackage = getClassCount(packageIndex);
		int[] _classes = getPackageClasses(packageIndex);
		int[] usage = new int[classesOnPackage];
		
		for (int i = 0; i < classesOnPackage; i++)
		{
			int classI = _classes[i];
			
			for (int j = 0; j < classesOnPackage; j++)
			{
				int classJ = _classes[j];
				
				if (dependencies[classI][classJ] > 0)
					usage[i]++;
			}
		}
		
		double sum = 0.0;
		
		for (int i = 0; i < classesOnPackage; i++)
			sum += usage[i] + 1;
		
		double classSquare = (double)classesOnPackage * (double)classesOnPackage;
		return (sum - classSquare) / (classesOnPackage - classSquare);
	}
	
	/**
	 * Calcula o LCOM5 do projeto
	 */
	public double calculateLCOM5()
	{
		double sum = 0.0;
		int count = 0;
		
		for (int i = 0; i < packageCount; i++)
			if (getClassCount(i) > 1)
			{
				sum += calculateLCOM5(i);
				count++;
			}
		
		return sum / count;
	}

	/**
	 * Calcula o CBO de um pacote, dado seu �ndice
	 */
	public int calculateCBO(int packageIndex)
	{
		boolean[] knownDependencies = new boolean[packageCount];
		int totalDependencies = 0;

		for (int i = 0; i < packageCount; i++)
			knownDependencies[i] = false;

		for (int i = 0; i < classCount; i++)
		{
			int currentPackage = newPackage[i];
			
			if (currentPackage == packageIndex)
			{
				for (int j = 0; j < classCount; j++)
				{
					int myPackage = newPackage[j];
			
					if (dependencies[i][j] > 0 && myPackage != currentPackage && !knownDependencies[myPackage])
					{
						totalDependencies++;
						knownDependencies[myPackage] = true;
					}
				}
			}
		}

		return totalDependencies;
	}

	/**
	 * Calcula o CBO de um projeto
	 */
	public double calculateCBO()
	{
		double sum = 0.0;
		int count = 0;
		
		for (int i = 0; i < packageCount; i++)
			if (getClassCount(i) > 0)
			{
				sum += calculateCBO(i);
				count++;
			}
		
		return sum / count;
	}

	/**
	 * Conta o n�mero de pacotes com apenas uma classe
	 */
	public int countSingleClassPackages()
	{
		int count = 0;

		for (int i = 0; i < packageCount; i++)
			if (newClasses[i] == 1)
				count++;

		return count;
	}
	
	public void add(int number)
	{
		sum += number;
		sumSquare += number * number;
		count++;
	}
	
	/**
	 * Returns the standard deviation of previously added numbers
	 */
	public double standardDeviation() 
	{
		return (count > 2) ? Math.sqrt(sumSquare / count - sum / count * sum / count) : 0.0;
	}

	/**
	 * Returns the average value of previously added numbers
	 */
	public double average() 
	{
		return (count > 0) ? sum / count : 0.0;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
//	N�mero de associa��es entre classes & Pacote & T01 \\ \hline

/**
 * 	N�mero de classe abstratas & Pacote & X02 \\ \hline
 */
	public int getAbstractClassesNumber() 
	{
		int AbstractClassesNumber=0;		
		for (int cls = 0; cls < this.project.getClassCount(); cls++) {
			if (this.project.getClassIndex(cls).isAbstract()) {
				AbstractClassesNumber= AbstractClassesNumber +1;	
			}
		}
		return AbstractClassesNumber;
	}
	
	
	/**
	 * N�mero de classe concretas & Pacote &  X02 \\ \hline	
	 */

	public int getConcreteClassesNumber() 
	{
		int ConcreteClassesNumber=0;		
		for (int cls = 0; cls < this.project.getClassCount(); cls++) {
			if (this.project.getClassIndex(cls).isAbstract()==false) {
				ConcreteClassesNumber++;	
			}
		}
		return ConcreteClassesNumber;
	}
	
	/**
	 * 	N�mero de classes & Pacote & C03, C09, A09, A11, A19, A22, A26, A29, A30  \\ \hline
	 */
	public int getClassesNumber() 
	{
		return this.project.getClassCount();
	}

	
	/**
	 * 	N�mero de classes com depend�ncias dentro do pacote & Pacote & H02, H12, C03, A02, A07, A09, A10 \\ \hline
	 */

	public int getIntraPackageDependenciesClassNumber() 
	{
		int intraPackageDependenciesClass=0;		
		for (int cls = 0; cls < this.project.getClassCount(); cls++) {
			for (int dep = 0; dep < this.project.getClassIndex(cls).getDependencyCount(); dep++) {
				String[] depClassName = this.project.getClassIndex(cls).getDependencyIndex(dep).getElementName().split("\\.");
				depClassName = Arrays.copyOf(depClassName , depClassName.length - 1);
				String depClassPackageName  = String.join (".", depClassName);
				if (this.project.getClassIndex(cls).getPackage().getName().equals(depClassPackageName )) {
					intraPackageDependenciesClass++;	
				}			
			}
		}
		return intraPackageDependenciesClass;
	}
	
	
	/**
	 * 	N�mero de depend�ncias entre classes & Pacote & T02, X03, C09, C11, A04, A06, A11, A13, A14, A19, A22, A26 \\ \hline
	 */
	
	public int getIntraPackageDependenciesNumber() 
	{
		return this.project.getDependencyCount();
	}
	

	/**
	 * 	N�mero de depend�ncias entre classes de pacotes diferentes & Pacote & X03, A13, A14, A19, A26, A27, A28\\ \hline
	 */

	public int getDiffertPackageDependenciesNumber() 
	{
		int differentPackageClassDependencies=0;	
		for (int cls = 0; cls < this.project.getClassCount(); cls++) {
			for (int dep = 0; dep < this.project.getClassIndex(cls).getDependencyCount(); dep++) {
				String[] depClassName = this.project.getClassIndex(cls).getDependencyIndex(dep).getElementName().split("\\.");
				depClassName = Arrays.copyOf(depClassName , depClassName.length - 1);
				String depClassPackageName  = String.join (".", depClassName);
				if (!this.project.getClassIndex(cls).getPackage().getName().equals(depClassPackageName )) {
					differentPackageClassDependencies++;	
				}			
			}
		}
		return differentPackageClassDependencies;
	}
	
	

//	
//	N�mero de classes com depend�ncias diretas dentro do pacote & Pacote & A17 \\ \hline
//	N�mero de classes de outro pacote com depend�ncias para classes de um pacote sob an�lise & Pacote & A02 \\ \hline
//	N�mero de classes do pacote sob an�lise com depend�ncias para classes de outro pacote & Pacote & A02 \\ \hline
//	N�mero de depend�ncias diretas entre classes & Pacote & A29 \\ \hline
//	N�mero m�ximo de superclasses & Pacote & H09  \\ \hline
//	N�mero de depend�ncias indiretas entre classes & Pacote & A30 \\ \hline 
//	N�mero de �rvores hier�rquicas de classes & Pacote & H13 \\ \hline 
//	N�mero de m�todos que n�o compartilham atributos de uma classe & Pacote & C01, C04, C05, C06, C07, C08  \\ \hline	
//	N�mero de pacotes cujas classes dependem das classes do pacote em an�lise & Pacote & X02, A25 \\ \hline
//	N�mero de pacotes dos quais as classes do pacote em an�lise dependem & Pacote & X02, A25 \\ \hline	

	
	/**
	 * 	N�mero de superclasses & Pacote & H01, H03, H17 \\ \hline
	 */
	
	public int getSuperclassPackageNumber() 
	{
		boolean existe = false;		
		Vector<Dependency> dependencies= new Vector<Dependency>();
		for (int cls = 0; cls < this.project.getClassCount(); cls++) {
			for (int dep = 0; dep < this.project.getClassIndex(cls).getDependencyCount(); dep++) {
				if(this.project.getClassIndex(cls).getDependencyIndex(dep).getType().equals(DependencyType.EXTENDS)) {
					existe=false;
					for (int x = 0; x < dependencies.size(); x++) {
						if (dependencies.elementAt(x).getElementName().equals(this.project.getClassIndex(cls).getDependencyIndex(dep).getElementName())){
							existe= true;
						}
					}
					if (!existe){				
						dependencies.add(this.project.getClassIndex(cls).getDependencyIndex(dep));
						
					}
				};
			}
		}
		return dependencies.size();
	}

	
	/**
	 * 	N�mero de subclasses & Pacote & H04, H17 \\ \hline
	 */
	
	public int getSubclassPackageNumber() 
	{
		int differentPackageClassDependencies=0;		
		for (int cls = 0; cls < this.project.getClassCount(); cls++) {
			for (int dep = 0; dep < this.project.getClassIndex(cls).getDependencyCount(); dep++) {
				if(this.project.getClassIndex(cls).getDependencyIndex(dep).getType().equals(DependencyType.EXTENDS)) {
					differentPackageClassDependencies++;
					break;
				};
			}
		}
		return differentPackageClassDependencies;
	}

	
//	N�mero de depend�ncias entre m�todos e atributos & Classe & C02, C10 \\ \hline 
//	N�mero de m�todos que compartilham atributos de uma classe & Classe & C01, C04, C05, C06, C07, C08  \\ \hline 
//	N�mero de m�todos herdados que foram reescritos em classe de outro pacote & Classe & X01 \\ \hline 
//	N�mero de m�todos herdados que n�o foram reescritos em classe de outro pacote & Classe & X01 \\ \hline 
//	N�mero de n�veis de hierarquia de classes filhas & Classe & H05 \\ \hline
//	N�mero de pacotes com depend�ncia & Sistema & A07, A10, A22, A26 \\ \hline
//	N�mero de subclasses & Classe & H11, H14, H15 \\ \hline
//	N�mero de subclasses diretas & Classe & H18 \\ \hline
//	N�mero de superclasses & Classe & H06, H07, H08, H10, H16 \\ \hline
//	N�mero de classes com depend�ncias dentro do pacote & Classe & A08,  A21, A24 \\ \hline	
//	N�mero de classes que dependem da classe em an�lise & Classe & A01, A23 \\ \hline
//	N�mero de depend�ncias entre classes & Classe & A03, A05, A06, A12, A15, A16, A18, A20 \\ \hline
	
}