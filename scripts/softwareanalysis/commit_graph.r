	project <-"JHotDraw"
	
	pdf(paste("D:/Backup/eclipse-workspace/PACOTE/result/images/", project, "_AffectedClasses.pdf", sep = ""), width=8,height=5)
	
	data <- read.table(paste("D:/Backup/eclipse-workspace/PACOTE/results/", project, "_RevisionsByVersion.data", sep = ""), header=TRUE);
	
		
	data$versions <- as.character(data$versions)

	if (project=="JHotDraw") {
		data[data$versions == "5.4.2", 1] <- "6.0.1" 
		data[data$versions == "7.2.0", 1] <- "7.3.0" 
	}
	
	if (project=="JEdit") {
		data[data$versions == "2.3.2", 1] <- "2.4.1" 
		data[data$versions == "2.3.3", 1] <- "2.4.1" 
		data[data$versions == "2.3.4", 1] <- "2.4.1" 
		data[data$versions == "2.3.5", 1] <- "2.4.1" 
		data[data$versions == "2.3.6", 1] <- "2.4.1" 
		data[data$versions == "2.3.7", 1] <- "2.4.1" 
		data[data$versions == "2.3.f", 1] <- "2.4.1" 
		data[data$versions == "2.4.2", 1] <- "2.4.f" 
		data[data$versions == "2.5.1", 1] <- "2.5.f" 
		data[data$versions == "3.0.1", 1] <- "3.0.2" 
		data[data$versions == "3.2.1", 1] <- "3.2.2" 
		data[data$versions == "4.0.0", 1] <- "4.0.3" 
		data[data$versions == "4.0.2", 1] <- "4.0.3"
		data[data$versions == "4.3.0", 1] <- "4.3.3"
		data[data$versions == "4.3.1", 1] <- "4.3.3"
		data[data$versions == "4.3.2", 1] <- "4.3.3" 
	}
	
	if (project=="JUnit") {
		data[data$versions == "4.8.0", 1] <- "4.8.1" 
	}

	data$versions <- as.factor(data$versions)


	# prepare the plots
	par(mfrow=c(2, 1), oma = c(0, 0, 0, 0), mai=c(.5,.5,.4,.1))
	boxplot(data$classes~data$versions, range=0, cex.axis=0.75, xlab='', ylab='', main="Classes afetadas por commits (com outliers)", las=2)
	boxplot(data$classes~data$versions, outline=FALSE, cex.axis=0.75, xlab='', ylab='',  main="Classes afetadas por commits  (sem outliers)", las=2)

	dev.off();
	
	pdf(paste("D:/Backup/eclipse-workspace/PACOTE/result/images/", project, "_AffectedPackages.pdf", sep = ""), width=8,height=5)
	data <- read.table(paste("D:/Backup/eclipse-workspace/PACOTE/results/", project, "_RevisionsByVersion.data", sep = ""), header=TRUE);

	data$versions <- as.character(data$versions)

	if (project=="JHotDraw") {
		data[data$versions == "5.4.2", 1] <- "6.0.1" 
		data[data$versions == "7.2.0", 1] <- "7.3.0" 
		
	}
	
	if (project=="JEdit") {
		data[data$versions == "2.3.2", 1] <- "2.4.1" 
		data[data$versions == "2.3.3", 1] <- "2.4.1" 
		data[data$versions == "2.3.4", 1] <- "2.4.1" 
		data[data$versions == "2.3.5", 1] <- "2.4.1" 
		data[data$versions == "2.3.6", 1] <- "2.4.1" 
		data[data$versions == "2.3.7", 1] <- "2.4.1" 
		data[data$versions == "2.3.f", 1] <- "2.4.1" 
		data[data$versions == "2.4.2", 1] <- "2.4.f" 
		data[data$versions == "2.5.1", 1] <- "2.5.f" 
		data[data$versions == "3.0.1", 1] <- "3.0.2" 
		data[data$versions == "3.2.1", 1] <- "3.2.2" 
		data[data$versions == "4.0.0", 1] <- "4.0.3" 
		data[data$versions == "4.0.2", 1] <- "4.0.3"
		data[data$versions == "4.3.0", 1] <- "4.3.3"
		data[data$versions == "4.3.1", 1] <- "4.3.3"
		data[data$versions == "4.3.2", 1] <- "4.3.3" 
	}
	
	if (project=="JUnit") {
		data[data$versions == "4.8.0", 1] <- "4.8.1" 
	}

	data$versions <- as.factor(data$versions)

	par(mfrow=c(2, 1), oma = c(0, 0, 0, 0), mai=c(.5,.5,.4,.1))
	boxplot(data$packages~data$versions, range=0, cex.axis=0.75, xlab='', ylab='', main="Pacotes afetados por commits (com outliers)", las=2)
	boxplot(data$packages~data$versions, outline=FALSE, cex.axis=0.75, xlab='', ylab='', main="Pacotes afetados por commits (sem outliers)", las=2)

	dev.off();
