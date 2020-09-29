
	#Analisando a interseção entre os pacotes das versões
	library(arsenal)
	
	project <-"JUnit"
	pdf(paste("D:/Backup/eclipse-workspace/PACOTE/results/", project, "_version_intersect_package.pdf", sep = ""), width=5,height=5)
	
	par(mfrow=c(4, 4), mar = c(0.1, 0.1, 0.1, 0.1))
	
	
	data <- read.table(paste("D:/Backup/eclipse-workspace/PACOTE/results/JARProjectCharacteristicsIntersect", project, ".data", sep = ""), header=TRUE);
	#data <- read.table(paste("D:/Backup/eclipse-workspace/PACOTE/results/JARProjectCharacteristics", project, ".data", sep = ""), header=TRUE);
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