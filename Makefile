%.exe: %.o
	clang -g -m64 -o $@ src/main/c/main.c $<

%.o: %.s
	nasm -f win64 -o $@ $<

%.s: %.int
	sbt --error "run $<" > $@
