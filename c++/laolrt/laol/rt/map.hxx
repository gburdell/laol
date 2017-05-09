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

            virtual LaolObj add(const LaolObj& self, const LaolObj& opB) const override {
                return merge(self, opB);
            }

            virtual LaolObj subscript(const LaolObj& self, const LaolObj& opB) const override {
                return find(self, opB);
            }

            virtual LaolObj toString(const LaolObj& self, const LaolObj& args) const override;

            virtual LaolObj subscript_assign(const LaolObj& self, const LaolObj& args) const override;

            //unique methods

            LaolObj size(const LaolObj& self, const LaolObj& args) const {
                return size();
            }

            // args is Map: i.e., {key,val}
            LaolObj merge(const LaolObj& self, const LaolObj& args) const;

            LaolObj merge_SELF(const LaolObj& self, const LaolObj& args) const;

            LaolObj empty_PRED(const LaolObj& self, const LaolObj& args) const;

            LaolObj key_PRED(const LaolObj& self, const LaolObj& args) const;

            LaolObj find(const LaolObj& self, const LaolObj& args) const;

            Map& operator=(const Map&) = delete;

            virtual ~Map();

		protected:
			virtual const METHOD_BY_NAME& getMethodByName() override;

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

