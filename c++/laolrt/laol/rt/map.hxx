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

        class Map : public Laol {
        public:
            typedef std::unordered_map<LaolObjKey, LaolObj> MAP;

            Map(std::initializer_list<MAP::value_type> init);

            //operators

            // + Add another map to this one to produce a new Map.

            virtual LaolObj add(LaolObj& self, LaolObj& opB) override {
                return merge(self, opB);
            }

            virtual LaolObj subscript(LaolObj& self, LaolObj& opB) override {
                return find(self, opB);
            }

            virtual LaolObj toString(LaolObj& self, LaolObj& args) override;

            virtual LaolObj subscript_assign(LaolObj& self, LaolObj& args) override;

            //unique methods

            LaolObj size(LaolObj& self, LaolObj& args) {
                return size();
            }

            // args is Map: i.e., {key,val}
            LaolObj merge(LaolObj& self, LaolObj& args) ;

            LaolObj merge_SELF(LaolObj& self, LaolObj& args) ;

            LaolObj empty_PRED(LaolObj& self, LaolObj& args) ;

            LaolObj key_PRED(LaolObj& self, LaolObj& args) ;

            LaolObj find(LaolObj& self, LaolObj& args) ;

            Laol::TPMethod getFunc(const string& methodNm) const override;

            Map& operator=(const Map&) = delete;

            virtual ~Map();

        private:
            Map(const Map& from); //clone

            size_t size() const {
                return m_map.size();
            }

            MAP m_map;
            static METHOD_BY_NAME stMethodByName;
        };

    }
}

#endif /* _laol_rt_map_hxx_ */

