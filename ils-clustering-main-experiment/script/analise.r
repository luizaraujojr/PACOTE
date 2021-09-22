rm(list = ls());
library(tidyverse);

data <- read_delim("\\Users\\User\\Downloads\\udt-java 56C.odem", delim=";") %>% 
  select(instance, round=runtime, evaluations=evaluationsConsumed, fitness=bestFitness, time=Time);

ggplot(data, aes(evaluations, fitness)) +
  facet_wrap(.~round) +
  geom_line();

maxFitness <- data %>% 
  group_by(instance, round) %>% 
  summarize(maxfitness = max(fitness), .groups="keep") %>% 
  ungroup();

hitTop <- data %>% 
  inner_join(maxFitness, by=c("instance", "round")) %>% 
  filter(fitness == maxfitness) %>% 
  group_by(instance, round) %>% 
  summarize(evaluation = min(evaluations), maxfitness = max(maxfitness), .groups="keep");

boxplot(hitTop$evaluation)
