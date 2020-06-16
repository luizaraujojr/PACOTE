
#Analisando a interseção entre os pacotes das versões


	library(arsenal)
	pdf("D:/Backup/eclipse-workspace/pacote/results/JHotDraw_version_intersect.pdf", width=5,height=5)
	par(mfrow=c(4, 4), mar = c(0.1, 0.1, 0.1, 0.1))
	data <- read.table("D:/Backup/eclipse-workspace/pacote/results/JARProjectCharacteristicsJhotdraw.data", header=TRUE);
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