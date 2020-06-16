	data <- read.table("D:/Backup/eclipse-workspace/PACOTE/results/jhotdraw_RevisionsByVersion.data", header=TRUE);
	#data <- read.table("D:/Backup/eclipse-workspace/PACOTE/results/jhotdraw_RevisionsByVersion.data", header=TRUE);
	#data <- read.table("D:/Backup/eclipse-workspace/PACOTE/results/junit_RevisionsByVersion.data", header=TRUE);


	versions <- sort(unique(data$version));
	#columns <- c("ncommit", "single_class", "single_class_percent", "single_package", "single_package_percent", "classes_commit_mean", "packages_commit_mean", "shapiro_p", "wilcox_class_p", "wilcox_package_p");
	columns <- c("ncommit", "single_class", "single_class_percent", "single_package", "single_package_percent", "classes_commit_mean", "packages_commit_mean")
	
	result <- matrix(nrow=length(versions), ncol=length(columns), dimnames=list(versions, columns));
	lastVersion <- "";
		
	for (version_ in versions)
	{
		versionData <- subset(data, version == version_);
		
		result[version_, "ncommit"] <- nrow(versionData);
		result[version_, "single_class"] <- nrow(subset(versionData, classes == 1)); 
		result[version_, "single_class_percent"] <- round(nrow(subset(versionData, classes == 1)) / nrow(versionData) *100,2); 
		
		result[version_, "single_package"] <- round(nrow(subset(versionData, packages == 1)),2); 
		result[version_, "single_package_percent"] <- round(nrow(subset(versionData, packages == 1)) / nrow(versionData) *100,2); 
		
		result[version_, "classes_commit_mean"] <- paste (round(mean(versionData$classes),2), "+-", round(sd(versionData$classes),2));
		#result[version_, "classes_commit_sd"] <- round(sd(versionData$classes),2);
		
		result[version_, "packages_commit_mean"] <- paste (round(mean(versionData$packages),2), "+-", round(sd(versionData$packages),2));
		#result[version_, "packages_commit_sd"] <- round(sd(versionData$packages),2);
		
		#if (version_ != versions[1])
		#{
			#lastVersionData <- subset(data, version == lastVersion);
			#result[version_, "wilcox_class_p"] <- round(wilcox.test(lastVersionData$classes, versionData$classes)$p.value,2);
			#result[version_, "wilcox_package_p"] <- round(wilcox.test(lastVersionData$packages, versionData$packages)$p.value,2);
		#}
		
		#if (nrow(versionData)>=3 & nrow(versionData)<=5000)
		#{
		#	result[version_, "shapiro_p"] <- round(shapiro.test(versionData$classes)$p.value,2);		
		#}
		
		lastVersion = version_;
	}

	result
	
	
	write.csv(result,file="D:/Backup/eclipse-workspace/PACOTE/results/commit_table.csv")

	
	
	mean(result[,"single_class_percent"])
	mean(result[,"single_package_percent"])

	pdf("D:/Backup/eclipse-workspace/pacote/results/jhotdraw_classesafetadas.pdf", width=8,height=10)
	data <- read.table("D:/Backup/eclipse-workspace/pacote/results/jhotdraw_RevisionsByVersion.data", header=TRUE);

	# prepare the plots
	par(mfrow=c(2, 1))
	boxplot(data$classes~data$version, range=0, cex.axis=0.75, xlab='', ylab='', main="Classes afetadas por commits (com outliers)")
	boxplot(data$classes~data$version, outline=FALSE, cex.axis=0.75, xlab='', ylab='',  main="Classes afetadas por commits  (sem outliers)")

	dev.off();
	
	
	pdf("D:/Backup/eclipse-workspace/pacote/results/jhotdraw_pacotesafetados.pdf", width=8,height=10)
	data <- read.table("D:/Backup/eclipse-workspace/pacote/results/jhotdraw_RevisionsByVersion.data", header=TRUE);

	par(mfrow=c(2, 1))
	boxplot(data$packages~data$version, range=0, cex.axis=0.75, xlab='', ylab='', main="Pacotes afetados por commits (com outliers)")
	boxplot(data$packages~data$version, outline=FALSE, cex.axis=0.75, xlab='', ylab='', main="Pacotes afetados por commits (sem outliers)")

	dev.off();
