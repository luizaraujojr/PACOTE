	project <-"JUnit"
	
	#JHotDraw_Exclude <- c("5.4.2");
	#JEdit_Exclude <- c("2.3.2", "2.3.3", "2.3.4", "2.3.5", "2.3.6", "2.3.7", "2.3.f", "2.4.2", "2.5.1", "3.0.1", "3.2.1", "4.0.0", "4.0.2", "4.3.0", "4.3.1", "4.3.2","5.4.0");
	#JEdit_Exclude <- c("4.8.0");
	
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
	


	#`%notin%` <- Negate(`%in%`)
	
	
	versions <- sort(unique(data$versions));
	#columns <- c("ncommit", "single_class", "single_class_percent", "single_package", "single_package_percent", "classes_commit_mean", "packages_commit_mean", "shapiro_p", "wilcox_class_p", "wilcox_package_p");
	columns <- c("ncommit", "single_class", "single_class_percent", "single_package", "single_package_percent", "classes_commit_mean", "classes_commit_mean_sd", "packages_commit_mean", "packages_commit_mean_sd")
	columns <- c("ncommit", "single_class", "single_class_percent", "single_package", "single_package_percent", "classes_commit_mean_sd",  "packages_commit_mean_sd")
	
	result <- matrix(nrow=length(versions), ncol=length(columns), dimnames=list(versions, columns));
	lastVersion <- "";
		
	for (version_ in versions)
	{
		versionData <- subset(data, versions == version_);
		
		result[version_, "ncommit"] <- nrow(versionData);
		result[version_, "single_class"] <- nrow(subset(versionData, classes == 1)); 
		result[version_, "single_class_percent"] <- round(nrow(subset(versionData, classes == 1)) / nrow(versionData) *100,2); 
		
		result[version_, "single_package"] <- round(nrow(subset(versionData, packages == 1)),2); 
		result[version_, "single_package_percent"] <- round(nrow(subset(versionData, packages == 1)) / nrow(versionData) *100,2); 
		
		#result[version_, "classes_commit_mean"] <- round(mean(versionData$classes),2);
		result[version_, "classes_commit_mean_sd"] <- paste ( "(",  round(median(versionData$classes),2) , ") ",  round(mean(versionData$classes),2), "+-", round(sd(versionData$classes),2));
		
		#result[version_, "packages_commit_mean"] <- round(mean(versionData$packages),2);		
		result[version_, "packages_commit_mean_sd"] <-  paste ( "(",  round(median(versionData$packages),2) , ") ", round(mean(versionData$packages),2), "+-", round(sd(versionData$packages),2));
		
		lastVersion = version_;
	}

	#result
	
	
	write.csv(result,file= paste("D:/Backup/eclipse-workspace/PACOTE/data/table/", project, "_commit_table.csv", sep = ""))

	sum(as.numeric(result[,"ncommit"]))
		
	mean(as.numeric(result[,"single_class_percent"]))
	mean(as.numeric(result[,"single_package_percent"]))
	mean(as.numeric(result[,"classes_commit_mean"]))
	mean(as.numeric(result[,"packages_commit_mean"]))

	max(as.numeric(result[,"single_class_percent"]))
	max(as.numeric(result[,"single_package_percent"]))
	max(as.numeric(result[,"classes_commit_mean"]))
	max(as.numeric(result[,"packages_commit_mean"]))

	min(as.numeric(result[,"single_class_percent"]))
	min(as.numeric(result[,"single_package_percent"]))
	min(as.numeric(result[,"classes_commit_mean"]))
	min(as.numeric(result[,"packages_commit_mean"]))