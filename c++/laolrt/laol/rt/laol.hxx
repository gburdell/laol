/*
 * The MIT License
 *
 * Copyright 2017 kpfalzer.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

/* 
 * File:   laol.hxx
 * Author: kpfalzer
 *
 * Created on April 1, 2017, 4:53 PM
 */

#ifndef _laol_rt_laol_hxx_
#define _laol_rt_laol_hxx_

#include <typeinfo>
#include <cmath>
#include <vector>
#include <array>
#include <string>
#include <map>
#include <memory>
#include "xyzzy/assert.hxx"
#include "laol/rt/exception.hxx"

#define NO_COPY_CONSTRUCTORS(_t)            \
    _t(const _t&) = delete;                 \
    _t& operator=(const _t&) = delete

#define NOT_SUPPORTED ASSERT_NEVER

namespace laol {
    namespace rt {
        using std::vector;
        using std::string;
        using std::array;
        using std::shared_ptr;

        template<typename T>
        inline string getClassName(T p) {
            return demangleName(typeid (p).name());
        }

        inline auto toObjectId(const void* p) {
            return reinterpret_cast<unsigned long int> (p);
        }

        class Laol;
        typedef shared_ptr<Laol> TRcLaol;

        class LaolObj;
        class Ref;
        extern const LaolObj NULLOBJ, TRUE, FALSE;

        // prefix with 'l' to not confuse with cout/cerr, endl
        extern const LaolObj lcerr;
        extern const LaolObj lcout;
        extern const LaolObj lendl; //newline and flush

        //Convenient type (for args) so we can pass {v1,v2,...}
        typedef const vector<LaolObj>& Args;

        // Generate 0-origin index from -n..n index.
        // Throw exception on out of bound.
        extern size_t actualIndex(long int ix, size_t length);

        class LaolObj {
        public:

            explicit LaolObj() {
            }

            //vector likes to use this...
            LaolObj(LaolObj&& r) = default;

            // Primitive: LaolRef lhs = val...

            // LaolObj a1 = std::array<LaolObj,2>{23,34};

            template<std::size_t N>
            LaolObj(const array<LaolObj, N>& r) {
                vector<LaolObj> tmp;
                tmp.insert(tmp.begin(), r.begin(), r.end());
                set(tmp);
            }

            // LaolObj v1 = std::vector<LaolObj>{56,78};

            LaolObj(Args r);

            //todo: this should use make_shared!

            LaolObj(Laol* rhs)
            : m_obj(rhs) {
            }

            //todo: this should use make_shared!

            LaolObj(const Laol* rhs)
            : LaolObj(const_cast<Laol*> (rhs)) {
            }

            LaolObj(int rhs);

            LaolObj(unsigned int rhs);

            LaolObj(long int rhs);

            LaolObj(unsigned long int rhs);

            LaolObj(char rhs);

            LaolObj(double rhs);

            LaolObj(float rhs);

            LaolObj(bool rhs);

            LaolObj(const char* rhs);

            LaolObj(TRcLaol* val) {
                NOT_SUPPORTED; //from whence we came?
            }

            LaolObj(const LaolObj& val);

            // New object/copy.  Not reference.
            LaolObj(const Ref& r);

            LaolObj operator=(const LaolObj& rhs);

            bool isNull() const {
                return (nullptr == asTPLaol());
            }

            // true if int variant
            bool isInt() const;

            // true if float/double variant
            bool isFloat() const;

            bool isNumeric() const {
                return isInt() || isFloat();
            }

            bool isBool() const;

            bool toBool() const;

            unsigned long int toUnsignedLongInt() const;

            unsigned int toUnsignedInt() const;

            size_t toSizet() const;

            int toInt() const;

            long int toLongInt() const;

            double toDouble() const;

            float toFloat() const;

            // Operator methods: i.e., can call directly
            LaolObj toString() const;
            LaolObj objectId() const;
            LaolObj hashCode() const;

            // Majority of operators are const, so we'll mark all
            // as const and unconst as necessary in Laol subclass.
            LaolObj operator<<(const LaolObj& opB) const;
            LaolObj operator>>(const LaolObj& opB) const;
            LaolObj operator+(const LaolObj& opB) const;
            LaolObj operator-(const LaolObj& opB) const;
            LaolObj operator*(const LaolObj& opB) const;
            LaolObj operator/(const LaolObj& opB) const;
            LaolObj operator%(const LaolObj& opB) const;

            //subscript
            virtual Ref operator[](const LaolObj& opB) const;
            //
            // baseline comparators.
            // (others are generated as function of these).
            //
            LaolObj operator==(const LaolObj& opB) const;
            LaolObj operator>(const LaolObj& opB) const;
            LaolObj operator<(const LaolObj& opB) const;

            //
            // generate comparators
            //            

            LaolObj operator!=(const LaolObj& opB) const {
                return !((*this == opB).toBool());
            }

            LaolObj operator<=(const LaolObj& opB) const {
                return (*this < opB).toBool() || (*this == opB).toBool();
            }

            LaolObj operator>=(const LaolObj& opB) const {
                return (*this > opB).toBool() || (*this == opB).toBool();
            }

            // incr/decr
            LaolObj operator++(int) const; //post
            LaolObj operator--(int) const; //post
            LaolObj operator++() const;
            LaolObj operator--() const;

            //
            // logical
            //
            LaolObj operator!() const;
            LaolObj operator||(const LaolObj& opB) const;
            LaolObj operator&&(const LaolObj& opB) const;

            //
            // bitwise
            //
            LaolObj operator~() const;
            LaolObj operator&(const LaolObj& opB) const;
            LaolObj operator|(const LaolObj& opB) const;
            LaolObj operator^(const LaolObj& opB) const;

            //TPMethod
            typedef Ref(Laol::* TPMethod)(const LaolObj& self, const LaolObj& args) const;

            //call method
            Ref operator()(const string& methodNm, const LaolObj& args, bool mustFind) const;
            virtual Ref operator()(const string& methodNm, const LaolObj& args) const;
            virtual Ref operator()(const string& methodNm) const;

            //call method if it exists (no error: as operator() does).

            Ref ifMethod(const string& methodNm, const LaolObj& args);

            //cast as subclass of Laol

            template<typename T>
            static const T& toType(const TRcLaol& r) {
                return dynamic_cast<const T&> (asTRCLaol(r));
            }

            template<typename T>
            static bool isA(const TRcLaol& r) {
                return (nullptr != dynamic_cast<T*> (asTPLaol(r)));
            }

            template<typename T>
            const T& toType() const {
                //ASSERT_TRUE(isA<T>());
                return toType<T>(m_obj);
            }

            template<typename T>
            bool isA() const {
                return isA<T>(m_obj);
            }

            bool isSameObject(const LaolObj& other) const;

            const std::type_info& typeInfo() const;

            // Convert to std::string with quoted string
            string toQString() const;

            virtual ~LaolObj();

        private:

            TRcLaol m_obj;

            const LaolObj& set(Args args);

            //From copy constructor or assign/= op.
            const LaolObj& set(const LaolObj& rhs);

            static
            const Laol& asTRCLaol(const TRcLaol& r) {
                return *r.get();
            }

            const Laol& asTRCLaol() const {
                return asTRCLaol(m_obj);
            }

            static
            Laol* asTPLaol(const TRcLaol& r) {
                return r.get();
            }

            static
            Laol* asTPLaol(TRcLaol& r) {
                return r.get();
            }

            Laol* asTPLaol() const {
                //ASSERT_TRUE(!isNull());
                return asTPLaol(m_obj);
            }

            string toStdString() const;

            friend class Laol; //to optimize operator=()
            friend struct LaolObjKey;
            friend class Symbol; //decrRefCnt()
            friend class Ref;
        };

        template<typename T>
        inline T* unconst(const T* p) {
            return const_cast<T*> (p);
        }

        inline LaolObj& unconst(const LaolObj& self) {
            return const_cast<LaolObj&> (self);
        }

        // Look like LaolObj, so can do foo[i].method...

        class Ref : public LaolObj {
        public:

            Ref()
            : LaolObj(), mp_ref(nullptr) {
            }

            // Constructor to create reference of actual/rvalue.

            Ref(const LaolObj* p)
            : LaolObj(*p) {
                mp_ref = const_cast<LaolObj*>(p);
            }

            Ref(const LaolObj& r)
            : LaolObj(r) {
                mp_ref = this;
            }

            Ref(const Ref& r) = default;
            Ref(Ref&& r) = default;

            // Constructor for primitive types.

#ifdef NO
            Ref(Laol* p)
            : LaolObj(p) {
                mp_ref = this;
            }
            
            template<typename T>
            Ref(T rhs)
            : LaolObj(rhs) {
                static_assert(
                              std::is_integral<T>::value
                              || std::is_floating_point<T>::value
                              || std::is_pointer<T>::value,
                              "Expect integral or pointer type.");
                mp_ref = this;
            }
#else
            // Explicitly declare, since template<T> leaves open pandoras box!
#define GEN_PRIM(_t) Ref(_t rhs) : LaolObj(rhs) {mp_ref = this;}
            GEN_PRIM(int);
            GEN_PRIM(unsigned int);
            GEN_PRIM(long int);
            GEN_PRIM(unsigned long int);
            GEN_PRIM(char);
            GEN_PRIM(double);
            GEN_PRIM(float);
            GEN_PRIM(bool);
            GEN_PRIM(const char*);
            GEN_PRIM(Laol*)
#undef GEN_PRIM
#endif
            
#ifdef NO
            // Change value and ref (to rhs) of object to which we refer
            const Ref& operator=(const LaolObj& rhs);
#endif
            
            // Change value and ref (to rhs) of object to which we refer
            const Ref& operator=(const Ref& rhs);
            
            Ref operator[](const LaolObj& subscript) const override;

            Ref operator()(const string& methodNm, const LaolObj& args) const override;

            Ref operator()(const string& methodNm) const override;

            // No subclass
            virtual ~Ref() final;

        private:
            LaolObj* mp_ref;

            friend class LaolObj;
        };

        inline
        Ref
        LaolObj::ifMethod(const string& methodNm, const LaolObj& args) {
            return (*this)(methodNm, args, false);
        }

        // LaolObj-like but with natural compare methods

        struct LaolObjKey : public LaolObj {

            template<typename T>
            LaolObjKey(T obj) : LaolObj(obj) {
            }

            //allow copy constructors

            bool operator==(const LaolObjKey& other) const {
                return LaolObj::operator==(other).toBool();
            }

            bool operator<(const LaolObjKey& other) const {
                return LaolObj::operator<(other).toBool();
            }

            bool operator>(const LaolObjKey& other) const {
                return LaolObj::operator>(other).toBool();
            }

            bool operator!=(const LaolObjKey& other) const {
                return !operator==(other);
            }

            bool operator<=(const LaolObjKey& other) const {
                return operator<(other) || operator==(other);
            }

            bool operator>=(const LaolObjKey& other) const {
                return operator>(other) || operator==(other);
            }

        };

        // Use toBool in 'if (expr)' -> 'if (toBool(expr))' in transpiler (laol -> c++).
        // Note: if 'operator bool()' defined, opens pandora box (of ambiguities)
        // and breaks the more natural primitive conversions: LaolObj(T val)...

        template<typename T>
        inline bool toBool(const T& expr) {
            return expr;
        }

        template<>
        inline bool toBool<LaolObj>(const LaolObj& expr) {
            return expr.toBool();
        }

        // Create vector via toV(obj1, obj2, ...)

        template<typename T>
        vector<LaolObj>
        toV(T r) {
            std::vector<LaolObj> sv;
            sv.push_back(r);
            return sv;
        }

        template<typename T, typename... Args>
        vector<LaolObj>
        toV(T first, Args... args) {
            auto r = toV(args...);
            r.insert(r.begin(), first);
            return r;
        }

        // Base class for any object

        class Laol {
        public:

            explicit Laol();

            NO_COPY_CONSTRUCTORS(Laol);

            //operators (mapped to mnemonic): so we can pass in 'self'
            //http://stackoverflow.com/questions/8679089/c-official-operator-names-keywords
            /* << */ virtual LaolObj left_shift(const LaolObj& self, const LaolObj& opB) const;
            /* >> */ virtual LaolObj right_shift(const LaolObj& self, const LaolObj& opB) const;
            /* +  */ virtual LaolObj add(const LaolObj& self, const LaolObj& opB) const;
            /* -  */ virtual LaolObj subtract(const LaolObj& self, const LaolObj& opB) const;
            /* *  */ virtual LaolObj multiply(const LaolObj& self, const LaolObj& opB) const;
            /* /  */ virtual LaolObj divide(const LaolObj& self, const LaolObj& opB) const;
            /* %  */ virtual LaolObj modulus(const LaolObj& self, const LaolObj& opB) const;
            /* =  */ virtual LaolObj assign(const LaolObj& self, const LaolObj& opB) const;

            /* [] */ virtual Ref subscript(const LaolObj& self, const LaolObj& range) const;

            /* == */ virtual LaolObj equal(const LaolObj& self, const LaolObj& opB) const;
            /* <  */ virtual LaolObj less(const LaolObj& self, const LaolObj& opB) const;
            /* >  */ virtual LaolObj greater(const LaolObj& self, const LaolObj& opB) const;
            /* !  */ virtual LaolObj negate(const LaolObj& self, const LaolObj&) const;
            /* ~  */ virtual LaolObj complement(const LaolObj& self, const LaolObj&) const;
            /* &  */ virtual LaolObj bitwise_and(const LaolObj& self, const LaolObj&) const;
            /* |  */ virtual LaolObj bitwise_or(const LaolObj& self, const LaolObj&) const;
            /* ^  */ virtual LaolObj bitwise_xor(const LaolObj& self, const LaolObj&) const;
            /* || */ virtual LaolObj logical_or(const LaolObj& self, const LaolObj& opB) const;
            /* && */ virtual LaolObj logical_and(const LaolObj& self, const LaolObj& opB) const;
            /*++(int) */ virtual LaolObj post_increment(const LaolObj& self, const LaolObj& opB) const;
            /*--(int) */ virtual LaolObj post_decrement(const LaolObj& self, const LaolObj& opB) const;
            /*++() */ virtual LaolObj pre_increment(const LaolObj& self, const LaolObj& opB) const;
            /*--() */ virtual LaolObj pre_decrement(const LaolObj& self, const LaolObj& opB) const;

            //Operator methods
            virtual LaolObj toString() const;
            virtual LaolObj objectId() const;
            virtual LaolObj hashCode() const;

            virtual ~Laol() = 0;

            typedef LaolObj::TPMethod TPMethod;

            TPMethod getFunc(const string& methodNm) const;
            typedef std::map<string, TPMethod> METHOD_BY_NAME;

            static
            METHOD_BY_NAME& join(METHOD_BY_NAME& onto, const METHOD_BY_NAME& from) {
                for (auto& kv : from) {
                    onto[kv.first] = kv.second;
                }
                return onto;
            }

            template<typename T1, typename... Args>
            static
            METHOD_BY_NAME& join(METHOD_BY_NAME& onto, T1 from, Args... args) {
                return join(join(onto, from), args...);
            }

            static string getClassName(const LaolObj& r);

            string getClassName() const;

        protected:
            virtual const METHOD_BY_NAME& getMethodByName();

        private:
            static const METHOD_BY_NAME stMethodByName;

            static TPMethod getFunc(const METHOD_BY_NAME& methodByName, const string& methodNm);
        };

#       // For 'builtin op LaolObj'
#define BINARY_OP(_op) \
        template<typename T> \
        inline LaolObj operator _op(T a, const LaolObj& b) { \
            return LaolObj(a).operator _op(b); \
        }

        BINARY_OP(+)
        BINARY_OP(-)
        BINARY_OP(*)
        BINARY_OP( /)
        BINARY_OP( %)
        BINARY_OP( <<)
        BINARY_OP(>>)
        BINARY_OP( ==)
        BINARY_OP( !=)
        BINARY_OP(<)
        BINARY_OP(>)
        BINARY_OP( ||)
        BINARY_OP(&&)
        BINARY_OP(&)
        BINARY_OP( |)
        BINARY_OP(^)

#undef BINARY_OP
#               
    }

}

namespace std {
    using laol::rt::LaolObjKey;

    template<>
    struct hash<LaolObjKey> {

        size_t operator()(const LaolObjKey& r) const {
            return r.hashCode().toSizet();
        }
    };
}

#endif /* _laol_rt_laol_hxx_ */

