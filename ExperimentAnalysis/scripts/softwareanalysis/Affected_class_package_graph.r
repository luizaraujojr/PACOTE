
	project<-"JUnit"

	pdf(paste("D:/Backup/eclipse-workspace/pacote/results/", project, "_AffectedClasses.pdf", sep = ""), width=8,height=6)
	data <- read.table(paste("D:/Backup/eclipse-workspace/pacote/results/", project, "_RevisionsByVersion.data", sep = ""), header=TRUE);

	# prepare the plots
	par(mfrow=c(2, 1))
	boxplot(data$classes~data$version, range=0, cex.axis=0.75, xlab='', ylab='', main="Classes afetadas por commits (com outliers)", las=2)
	boxplot(data$classes~data$version, outline=FALSE, cex.axis=0.75, xlab='', ylab='',  main="Classes afetadas por commits  (sem outliers)", las=2)

	dev.off();
	

	pdf(paste("D:/Backup/eclipse-workspace/pacote/results/", project, "_AffectedPackages.pdf", sep = ""), width=8,height=6)
	data <- read.table(paste("D:/Backup/eclipse-workspace/pacote/results/", project, "_RevisionsByVersion.data", sep = ""), header=TRUE);

	par(mfrow=c(2, 1))
	boxplot(data$packages~data$version, range=0, cex.axis=0.75, xlab='', ylab='', main="Pacotes afetados por commits (com outliers)", las=2)
	boxplot(data$packages~data$version, outline=FALSE, cex.axis=0.75, xlab='', ylab='', main="Pacotes afetados por commits (sem outliers)", las=2)

	dev.off();
