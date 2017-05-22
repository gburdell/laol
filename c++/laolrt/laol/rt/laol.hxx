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
#include "xyzzy/assert.hxx"
#include "xyzzy/refcnt.hxx"
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

        template<typename T>
        inline string getClassName(T p) {
            return demangleName(typeid (p).name());
        }

        inline auto toObjectId(const void* p) {
            return reinterpret_cast<unsigned long int> (p);
        }

        using xyzzy::TRcObj;
        using xyzzy::PTRcObjPtr;

        class Laol;
        typedef PTRcObjPtr<Laol> TRcLaol;

        class LaolObj;
        class Ref;
        extern const LaolObj NULLOBJ;

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

            LaolObj(Laol* rhs)
            : m_obj(rhs) {
            }

            LaolObj(const Laol* rhs) {
                NOT_SUPPORTED;
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

            LaolObj(TRcLaol* val)
            : m_obj(*val) {
            }

            LaolObj(const LaolObj& val);

            LaolObj(const Ref& r);

            LaolObj(const LaolObj* val) {
                NOT_SUPPORTED;
            }

            LaolObj operator=(const LaolObj& rhs);

            //The move constructor is used.
            //We'll use the default.  But, not quite sure what
            //it does w/ the reference counted stuff...
            //Might be safer to just do copy...
            LaolObj(LaolObj&&) = default;

            bool isNull() const {
                return m_obj.isNull();
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
            typedef LaolObj(Laol::* TPMethod)(const LaolObj& self, const LaolObj& args) const;

            //call method
            LaolObj operator()(const string& methodNm, const LaolObj& args, bool mustFind = true) const;
            LaolObj operator()(const string& methodNm) const;

            //call method if it exists (no error: as operator() does).

            LaolObj ifMethod(const string& methodNm, const LaolObj& args) {
                return (*this)(methodNm, args, false);
            }

            //cast as subclass of Laol

            template<typename T>
            const T& toType() const {
                ASSERT_TRUE(isA<T>());
                return dynamic_cast<const T&> (asTPRcLaol()->asT());
            }

            template<typename T>
            bool isA() const {
                return (0 != dynamic_cast<const T*> (asTPLaol()));
            }

            bool isSameObject(const LaolObj& other) const;

            // Convert to std::string with quoted string
            string toQString() const;

            virtual ~LaolObj();

        private:

            TRcLaol m_obj;

            const LaolObj& set(Args args);

            //From copy constructor or assign/= op.
            const LaolObj& set(const LaolObj& rhs);

            TRcLaol* asTPRcLaol() const {
                return const_cast<TRcLaol*> (&m_obj);
            }

            TRcLaol& asTRcLaol() const {
                return const_cast<TRcLaol&> (m_obj);
            }

            Laol* asTPLaol() const {
                ASSERT_TRUE(!isNull());
                return asTPRcLaol()->getPtr();
            }

            string toStdString() const;

            LaolObj hashCode(const LaolObj&) const {
                return NULLOBJ; //todo objectId(NULLOBJ);
            }

            size_t hashCode() const {
                return 0; //todo
            }

            void decrRefCnt();

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

        // Look like LaolRef, so can do foo[i].method...

        class Ref : public LaolObj {
        public:

            Ref(const LaolObj& r) : LaolObj(r), m_ref(unconst(r)) {
            }

            const Ref& operator=(const LaolObj& rhs);

            Ref operator[](const LaolObj& subscript) const override;

            Ref(Ref& r) : LaolObj(r.m_ref), m_ref(r.m_ref) {
            }

            const Ref& operator=(const Ref& r);

            // Allow move constructor to easily pass back return vals
            Ref(Ref&& from) = default;

        private:
            LaolObj& m_ref;

            friend class LaolObj;
        };

        // LaolObj-like but with natural compare methods

        struct LaolObjKey : public LaolObj {

            template<typename T>
            LaolObjKey(T obj) : LaolObj(obj) {
            }

            size_t hashCode() const {
                return LaolObj::hashCode();
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

        class Laol : public TRcObj {
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

            /* [] */ virtual Ref subscript(const LaolObj& self, const LaolObj& opB) const;

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

            //Map special methods for call-by-method too
            virtual LaolObj toString(const LaolObj&, const LaolObj&) const;
            virtual LaolObj objectId(const LaolObj&, const LaolObj&) const;
            virtual LaolObj hashCode(const LaolObj&, const LaolObj&) const;

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

        protected:
            virtual const METHOD_BY_NAME& getMethodByName();

        private:
            static TPMethod getFunc(const METHOD_BY_NAME& methodByName, const string& methodNm);

            static const METHOD_BY_NAME stMethodByName;

            auto objectId() const {
                return toObjectId(this);
            }

        };
    }
}

namespace std {
    using laol::rt::LaolObjKey;

    template<>
    struct hash<LaolObjKey> {

        size_t operator()(const LaolObjKey& r) const {
            return r.hashCode();
        }
    };
}

#endif /* _laol_rt_laol_hxx_ */

