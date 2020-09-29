	# box-plot for the number of classes per package
	project <-"JEdit"
	pdf(paste("D:/Backup/eclipse-workspace/PACOTE/results/", project, "_classpackage.pdf", sep = ""), width=8,height=3)
	data_classespackages <- read.table(paste("D:/Backup/eclipse-workspace/PACOTE/results/JARProjectCharacteristics", project, ".data", sep = ""), header=TRUE);
	
	JEdit_Exclude <- as.factor(c("2.3.2", "2.3.3", "2.3.4", "2.3.5", "2.3.6", "2.3.7", "2.3.f", "2.4.2", "2.5.1", "3.0.1", "3.2.1", "4.0.0", "4.0.2", "4.3.0", "4.3.1", "4.3.2","5.4.0"));

	`%notin%` <- Negate(`%in%`)
	
	data_classespackages = subset(data_classespackages, data_classespackages$versions %notin% JEdit_Exclude)
	
	unique_versions <- unique(data_classespackages$versions);
	uniqueVersionPackage <- unique(data_classespackages[,c("versions","packages")])
	colnames <- c("version", "package", "ClassesPackages");
	result1 <- matrix(nrow=nrow(uniqueVersionPackage),  ncol=length(colnames))
	colnames(result1) <- c("version", "package", "ClassesPackages");

	i <-1
	for (version_ in unique_versions)
	{
		vdata <- subset(data_classespackages, versions == version_);
		unique_packages <- unique(vdata$packages);
		for (packages_ in unique_packages)
		{
			zdata <- subset(vdata, packages == packages_);
			result1[i, 1] <- version_;#substring (version_,1,nchar(version_)-4); 
			result1[i, 2] <- packages_; 
			result1[i, 3] <- length(zdata$classes); 
			
			i<- i+1
		}
	}
	
	par(mar=c(5,3,3,3), oma = c(0, 0, 0, 0), mai=c(.7,.5,.5,.1))
	boxplot(as.numeric(result1[,3]) ~ result1[,1], data = result1, main="Classes/Pacotes", xlab='', las=2)
	dev.off();

	# box-plot for the number of attributes per class
	
	pdf(paste("D:/Backup/eclipse-workspace/PACOTE/results/", project, "_classattribute.pdf", sep = ""), width=8,height=3)
	data <- read.table(paste("D:/Backup/eclipse-workspace/PACOTE/results/JARProjectCharacteristics", project, ".data", sep = ""), header=TRUE);
	
	JEdit_Exclude <- as.factor(c("2.3.2", "2.3.3", "2.3.4", "2.3.5", "2.3.6", "2.3.7", "2.3.f", "2.4.2", "2.5.1", "3.0.1", "3.2.1", "4.0.0", "4.0.2", "4.3.0", "4.3.1", "4.3.2","5.4.0"));

	`%notin%` <- Negate(`%in%`)
	
	
	data = subset(data, data$versions %notin% JEdit_Exclude)
	par(mar=c(5,3,3,3), oma = c(0, 0, 0, 0), mai=c(.7,.5,.5,.1))
	boxplot(data[,"attrs"] ~data[,"versions"], data= data, main="Atributos/Classe", xlab='', las=2)
	dev.off();


	# box-plot for the number of methods per class
	pdf(paste("D:/Backup/eclipse-workspace/PACOTE/results/", project, "_classmethod.pdf", sep = ""), width=8,height=3)
	data <- read.table(paste("D:/Backup/eclipse-workspace/PACOTE/results/JARProjectCharacteristics", project, ".data", sep = ""), header=TRUE);
	data = subset(data, data$versions %notin% JEdit_Exclude)
	par(mar=c(5,3,3,3), oma = c(0, 0, 0, 0), mai=c(.7,.5,.5,.1))
	boxplot(data[,"meths"] ~data[,"versions"], data= data, main="Métodos/Classe", xlab='', las=2)
	dev.off();
	

	# box-plot for the number of public methods per class
	pdf(paste("D:/Backup/eclipse-workspace/PACOTE/results/", project, "_classpublicmethod.pdf", sep = ""), width=8,height=3)
	data <- read.table(paste("D:/Backup/eclipse-workspace/PACOTE/results/JARProjectCharacteristics", project, ".data", sep = ""), header=TRUE);
	data = subset(data, data$versions %notin% JEdit_Exclude)
	par(mar=c(5,3,3,3), oma = c(0, 0, 0, 0), mai=c(.7,.5,.5,.1))
	boxplot(data[,"pmeths"] ~data[,"versions"], data= data, main="Métodos públicos/Classe", xlab='', las=2)
	dev.off();


