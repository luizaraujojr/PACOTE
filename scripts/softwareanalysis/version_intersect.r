
	#Analisando a interseção entre os pacotes das versões
	library(arsenal)
	
	project <-"JEdit"
	pdf(paste("D:/Backup/eclipse-workspace/PACOTE/results/images/", project, "_version_intersect_package.pdf", sep = ""), width=5,height=5)
	
	par(mfrow=c(4, 4), mar = c(0.1, 0.1, 0.1, 0.1))
	
	#data <- read.table(paste("D:/Backup/eclipse-workspace/PACOTE/results/JARProjectCharacteristicsIntersect", project, ".data", sep = ""), header=TRUE);
	data <- read.table(paste("D:/Backup/eclipse-workspace/PACOTE/results/JARProjectCharacteristics", project, ".data", sep = ""), header=TRUE);
	
	JHotDraw_Exclude <- c("5.4.2");
	JEdit_Exclude <- as.factor(c("2.3.2", "2.3.3", "2.3.4", "2.3.5", "2.3.6", "2.3.7", "2.3.f", "2.4.2", "2.4.f", "2.5.1", "3.0.0", "3.0.1", "3.0.2", "3.2.1", "3.2.2", "4.0.0", "4.0.2", "4.3.0", "4.3.1", "4.3.2", "4.4.2", "4.5.1", "4.5.2", "5.2.0", "5.3.0", "5.4.0"));
	
	`%notin%` <- Negate(`%in%`)

	data$versions <- as.character(data$versions)

	if (project=="JHotDraw"	){
		data <- subset(data, versions %notin% JHotDraw_Exclude)
	}

	if (project=="JEdit"	){
		data <- subset(data, versions %notin% JEdit_Exclude)
	}

	data$versions <- as.factor(data$versions)

		
	
	unique_versions <- unique(data$versions);
	colnames <- c("Packages", "Classes", "Attrs", "Meths", "PMeths", "NAC");
	result1 <- matrix(nrow=length(unique_versions), ncol=length(colnames), dimnames=list(unique_versions, colnames));
	previous_version <- "";
	for (version_ in unique_versions)
	{
		if (previous_version != "") {
			first <- unique(subset(data, versions == previous_version)$packages);
			second <- unique(subset(data, versions == version_)$packages);
			
			both <-  intersect(first, second)
			onlyfirst <- setdiff(first, second)
			onlysecond <- setdiff(second, first)
			
			require("gplots")
			
			list_ <- list(second, first)
			names(list_) <- c(version_, previous_version)
			venn(list_)
		}
		previous_version <- version_;
	}
	dev.off();
	
	
	
	library(arsenal)
		
	
	pdf(paste("D:/Backup/eclipse-workspace/PACOTE/results/", project, "_version_intersect_class.pdf", sep = ""), width=5,height=5)
	
	par(mfrow=c(4, 4), mar = c(0.1, 0.1, 0.1, 0.1))
	
		
	#data <- read.table(paste("D:/Backup/eclipse-workspace/PACOTE/results/JARProjectCharacteristicsIntersect", project, ".data", sep = ""), header=TRUE);
	data <- read.table(paste("D:/Backup/eclipse-workspace/PACOTE/results/JARProjectCharacteristics", project, ".data", sep = ""), header=TRUE);
	
	
	
	
	JHotDraw_Exclude <- c("5.4.2");
	JEdit_Exclude <- as.factor(c("2.3.2", "2.3.3", "2.3.4", "2.3.5", "2.3.6", "2.3.7", "2.3.f", "2.4.2", "2.5.1", "3.0.1", "3.2.1", "4.0.0", "4.0.2", "4.3.0", "4.3.1", "4.3.2","5.4.0"));

	`%notin%` <- Negate(`%in%`)

	data$versions <- as.character(data$versions)

	if (project=="JHotDraw"	){
		data <- subset(data, versions %notin% JHotDraw_Exclude)
	}

	if (project=="JEdit"	){
		data <- subset(data, versions %notin% JEdit_Exclude)
	}

	data$versions <- as.factor(data$versions)


	
	unique_versions <- unique(data$versions);
	colnames <- c("Packages", "Classes", "Attrs", "Meths", "PMeths", "NAC");
	result1 <- matrix(nrow=length(unique_versions), ncol=length(colnames), dimnames=list(unique_versions, colnames));
	previous_version <- "";
	for (version_ in unique_versions)
	{
		if (previous_version != "") {
			first <- unique(subset(data, versions == previous_version)$classes);
			second <- unique(subset(data, versions == version_)$classes);
			
			both <-  intersect(first, second)
			onlyfirst <- setdiff(first, second)
			onlysecond <- setdiff(second, first)
			
			require("gplots")
			
			list_ <- list(second, first)
			names(list_) <- c(version_, previous_version)
			venn(list_)
		}
		previous_version <- version_;
	}
		dev.off();
	
	 subset(data, versions == "2.00.0")