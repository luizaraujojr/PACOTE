

#data <- read.table("D:/Backup/eclipse-workspace/PACOTE/results/jhotdraw_RevisionsByVersion.data", header=TRUE);
#data <- read.table("D:/Backup/eclipse-workspace/PACOTE/results/jedit_RevisionsByVersion.data", header=TRUE);
data <- read.table("D:/Backup/eclipse-workspace/PACOTE/results/junit_RevisionsByVersion.data", header=TRUE);

versions <- sort(unique(data$version));

columns <- c("team", "inTeam", "outTeam", "num_commits", "NAR");
result <- matrix(nrow=length(versions), ncol=length(columns), dimnames=list(versions, columns));

oldTeam <- c();

for (version_ in versions)
{
	vdata <- subset(data, version == version_);
	developers <- split(vdata, vdata$author);

	team <- unique(vdata$author);
	inTeam <- setdiff(team, oldTeam);
	outTeam <- setdiff(oldTeam, team);
	
	oldTeam <- team;

	commits <- unlist(lapply(developers, nrow));
	commits <- subset(commits, commits > 0);
	print(commits);

	result[version_, "team"] <- length(team);
	result[version_, "inTeam"] <- length(inTeam);
	result[version_, "outTeam"] <- length(outTeam);
	result[version_, "num_commits"] <- nrow(vdata);
	result[version_, "NAR"] <- sd(commits);
}

result
