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
 * File:   map.hxx
 * Author: kpfalzer
 *
 * Created on April 20, 2017, 3:55 PM
 */

#ifndef _laol_rt_map_hxx_
#define _laol_rt_map_hxx_

#include <unordered_map>
#include "laol/rt/laol.hxx"

namespace laol {
    namespace rt {

        struct LaolObjKey : public LaolObj {

            template<typename T>
            LaolObjKey(T obj) : LaolObj(obj) {
            }

            size_t hashCode() const {
                return unconst(*this)("hashCode", NULLOBJ).toLInt();
            }

            //allow copy constructors

            bool operator==(const LaolObjKey& other) const {
                return LaolObj::operator==(other).toBool();
            }
        };
    }
}

inline
bool operator==(const laol::rt::LaolObjKey& opA, const laol::rt::LaolObjKey& opB) {
    return opA.operator==(opB);
}

namespace std {
    using laol::rt::LaolObjKey;
    using laol::rt::NULLOBJ;

    template<>
    struct hash<LaolObjKey> {

        size_t operator()(const LaolObjKey& r) const {
            return r.hashCode();
        }
    };
};

namespace laol {
    namespace rt {

        class Map : public Laol {
        public:
            typedef std::unordered_map<LaolObjKey, LaolObj> MAP;

            Map(std::initializer_list<MAP::value_type> init);

            //operators

            // + Add another map to this one to produce a new Map.

            virtual LaolObj add(const LaolObj& self, const LaolObj& opB) const override {
                return merge(self, opB);
            }

            //unique methods

            LaolObj size(const LaolObj& self, const LaolObj& args) const {
                return size();
            }

            LaolObj merge(const LaolObj& self, const LaolObj& args) const;

            LaolObj merge_SELF(const LaolObj& self, const LaolObj& args) const;

            // args is Map: i.e., {key,val}
            LaolObj insert_SELF(const LaolObj& self, const LaolObj& args) const;

            LaolObj empty_PRED(const LaolObj& self, const LaolObj& args) const;

            Laol::TPMethod getFunc(const string& methodNm) const override;

            NO_COPY_CONSTRUCTORS(Map);

            virtual ~Map();

        private:

            size_t size() const {
                return m_map.size();
            }

            MAP m_map;
            static METHOD_BY_NAME stMethodByName;
        };

    }
}

#endif /* _laol_rt_map_hxx_ */

