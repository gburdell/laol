#include "laol/user/logger.hxx"

namespace laol {
    namespace user {
        extern Ref main(const LaolObj& self, const LaolObj& args);
    }
}

int main(int, char**) {
    laol::user::main(NULLOBJ, NULLOBJ);
    return EXIT_SUCCESS;
}
