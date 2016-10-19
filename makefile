#makefile

all:
	@echo "# Compilation des classes"
	find -name "*.java" | xargs javac -d bin -Xlint:unchecked

launch:
	@echo "# Execution de la classe Main"
	java -cp bin Main

calculatrice:
	@echo "# Execution de la classe calculatric.Main"
	java -cp bin calculatrice.Main

clean:
	@echo "# Nettoyage du dossier bin"
	rm -R bin/*