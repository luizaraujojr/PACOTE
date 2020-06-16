
	pdf("D:/Backup/eclipse-workspace/pacote/results/JHotDraw_AffectedClasses.pdf", width=8,height=6)
	data <- read.table("D:/Backup/eclipse-workspace/pacote/results/jhotdraw_RevisionsByVersion.data", header=TRUE);

	# prepare the plots
	par(mfrow=c(2, 1))
	boxplot(data$classes~data$version, range=0, cex.axis=0.75, xlab='', ylab='', main="Classes afetadas por commits (com outliers)", las=2)
	boxplot(data$classes~data$version, outline=FALSE, cex.axis=0.75, xlab='', ylab='',  main="Classes afetadas por commits  (sem outliers)", las=2)

	dev.off();
	
	
	pdf("D:/Backup/eclipse-workspace/pacote/results/JHotDraw_AffectedPackages.pdf", width=8,height=6)
	data <- read.table("D:/Backup/eclipse-workspace/pacote/results/jhotdraw_RevisionsByVersion.data", header=TRUE);

	par(mfrow=c(2, 1))
	boxplot(data$packages~data$version, range=0, cex.axis=0.75, xlab='', ylab='', main="Pacotes afetados por commits (com outliers)", las=2)
	boxplot(data$packages~data$version, outline=FALSE, cex.axis=0.75, xlab='', ylab='', main="Pacotes afetados por commits (sem outliers)", las=2)

	dev.off();
