#data <- read.table("/Users/Marcio Barros/Documents/GitHub/sbse-ant-unirio/results/versioncontrol/log_versions.data", header=TRUE);

#data <- read.table("D:/Backup/eclipse-workspace/projetotese/results/jedit_versions.data", header=TRUE);

data$version[data$version == "1.4.1"] <- "1.4.0";
data$version[data$version == "1.5.1"] <- "1.5.0";
data$version[data$version == "1.5.2"] <- "1.5.0";
data$version[data$version == "1.5.3"] <- "1.5.0";
data$version[data$version == "1.5.4"] <- "1.5.0";
data$version[data$version == "1.6.1"] <- "1.6.0";
data$version[data$version == "1.6.2"] <- "1.6.0";
data$version[data$version == "1.6.3"] <- "1.6.0";
data$version[data$version == "1.6.4"] <- "1.6.0";
data$version[data$version == "1.6.5"] <- "1.6.0";
data$version[data$version == "1.7.1"] <- "1.7.0";
data$version[data$version == "1.8.1"] <- "1.8.0";
data$version[data$version == "1.8.3"] <- "1.8.2";
data$version[data$version == "1.8.4"] <- "1.8.2";
data$version <- factor(data$version);
#data$version <- factor(data$version);


	#data <- read.table("D:/Backup/eclipse-workspace/PACOTE/results/jhotdraw_RevisionsByVersion.data", header=TRUE);
	#data <- read.table("D:/Backup/eclipse-workspace/PACOTE/results/jedit_RevisionsByVersion.data", header=TRUE);
	data <- read.table("D:/Backup/eclipse-workspace/PACOTE/results/junit_RevisionsByVersion.data", header=TRUE);


	versions <- sort(unique(data$version));
	columns <- c("rev", "single_class", "single_class_percent", "single_package", "single_package_percent", "classes_commit_mean", "classes_commit_sd", "packages_commit_mean", "packages_commit_sd", "shapiro_p", "wilcox_class_p", "wilcox_package_p");
	result <- matrix(nrow=length(versions), ncol=length(columns), dimnames=list(versions, columns));
	lastVersion <- "";
		
	for (version_ in versions)
	{
		versionData <- subset(data, version == version_);
		
		result[version_, "rev"] <- nrow(versionData);
		result[version_, "single_class"] <- nrow(subset(versionData, classes == 1)); 
		result[version_, "single_class_percent"] <- nrow(subset(versionData, classes == 1)) / nrow(versionData) *100; 
		
		result[version_, "single_package"] <- nrow(subset(versionData, packages == 1)); 
		result[version_, "single_package_percent"] <- nrow(subset(versionData, packages == 1)) / nrow(versionData) *100; 
		
		result[version_, "classes_commit_mean"] <- mean(versionData$classes);
		result[version_, "classes_commit_sd"] <- sd(versionData$classes);
		
		result[version_, "packages_commit_mean"] <- mean(versionData$packages);
		result[version_, "packages_commit_sd"] <- sd(versionData$packages);
		
		if (version_ != versions[1])
		{
			lastVersionData <- subset(data, version == lastVersion);
			result[version_, "wilcox_class_p"] <- wilcox.test(lastVersionData$classes, versionData$classes)$p.value;
			result[version_, "wilcox_package_p"] <- wilcox.test(lastVersionData$packages, versionData$packages)$p.value;
		}
		
		if (nrow(versionData)>=3 & nrow(versionData)<=5000)
		{
			result[version_, "shapiro_p"] <- shapiro.test(versionData$classes)$p.value;		
		}
		
		lastVersion = version_;
	}

	result


	data <- read.table("D:/Backup/eclipse-workspace/pacote/results/junit_RevisionsByVersion.data", header=TRUE);

	# prepare the plots
	#pdf("c:/Users/Marcio Barros/Desktop/revision_classes.pdf", width=16, height=5)
	par(mfrow=c(2, 1))
	boxplot(data$classes~data$version, range=0, cex.axis=0.75, xlab='', ylab='', main="Classes afetadas por commits (com outliers)")
	boxplot(data$classes~data$version, outline=FALSE, cex.axis=0.75, xlab='', ylab='',  main="Classes afetadas por commits  (sem outliers)")

#dev.off();

	data <- read.table("D:/Backup/eclipse-workspace/pacote/results/junit_RevisionsByVersion.data", header=TRUE);

#pdf("c:/Users/Marcio Barros/Desktop/revision_packages.pdf", width=16, height=5)
	par(mfrow=c(2, 1))
	boxplot(data$packages~data$version, range=0, cex.axis=0.75, xlab='', ylab='', main="Pacotes afetados por commits (com outliers)")
	boxplot(data$packages~data$version, outline=FALSE, cex.axis=0.75, xlab='', ylab='', main="Pacotes afetados por commits (sem outliers)")

#dev.off();
