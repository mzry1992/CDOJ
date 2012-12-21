#ifndef __COMPILER_FACTORY_H__
#define __COMPILER_FACTORY_H__

#include <string>
#include <map>
#include "Compiler.h"

class CompilerFactory {
public:
	static const CompilerFactory* getInstance(void);
	const Compiler* getCompiler(int id) const;
	const Compiler* getCompiler(std::string extension) const;
private:
	CompilerFactory(void);
	static std::map<int, const Compiler*> compilers;
	static CompilerFactory* instance;
};

#endif // __COMPILER_FACTORY_H__
