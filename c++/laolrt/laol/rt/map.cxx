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

#include <sstream>
#include "laol/rt/string.hxx"
#include "laol/rt/array.hxx"
#include "laol/rt/map.hxx"


namespace laol {
    namespace rt {
        //static
        Laol::METHOD_BY_NAME Map::stMethodByName;

        Map::Map(std::initializer_list<MAP::value_type> init)
        : m_map(init) {

        }

        Map::Map(const Map& from) {
            for (auto& ele : from.m_map) {
                m_map[ele.first] = ele.second;
            }
        }

        /*LaolObj
        Map::subscript_assign(const LaolObj& self, const LaolObj& args) const {
            ASSERT_TRUE(args.isA<Array>());
            const Array& vargs = args.toType<Array>();
            const auto N = vargs.length(args, NULLOBJ).toLInt();
            if (2 != N) {
                throw ArityException(N, "2");
            }
            LaolObj key = vargs[0], val = vargs[1];
            unconst(this)->m_map[key] = val;
            return self;
        }*/

        LaolObj
        Map::toString() const {
            std::ostringstream oss;
            oss.operator << ("{");
            bool doComma = false;
            for (auto& ele : m_map) {
                if (doComma) {
                    oss.operator << (", ");
                }
                oss << ele.first.toQString() << ": " << ele.second.toQString();
                doComma = true;
            }
            oss.operator << ("}");
            return new String(oss.str());
        }

        Ref
        Map::empty_PRED(const LaolObj& self, const LaolObj& args) const {
            return LaolObj(m_map.empty());
        }

        Ref
        Map::merge_SELF(const LaolObj& self, const LaolObj& args) const {
            const Map& from = args.toType<Map>();
            for (auto& ele : from.m_map) {
                unconst(this)->m_map[ele.first] = ele.second;
            }
            return self;
        }

        Ref
        Map::merge(const LaolObj& self, const LaolObj& args) const {
            LaolObj copy = new Map(*this);
            return copy.toType<Map>().merge_SELF(copy, args);
        }

        Ref
        Map::key_PRED(const LaolObj& self, const LaolObj& args) const {
            return LaolObj((m_map.end() != m_map.find(args)));
        }

        Ref
        Map::find(const LaolObj& self, const LaolObj& args) const {
            auto it = m_map.find(args);
            return (it != m_map.end()) ? it->second : NULLOBJ;
        }

        const Laol::METHOD_BY_NAME&
        Map::getMethodByName() {
            if (stMethodByName.empty()) {
                stMethodByName = Laol::join(stMethodByName,
				METHOD_BY_NAME({
                    {"size", static_cast<TPMethod> (&Map::size)},
                    {"empty?", static_cast<TPMethod> (&Map::empty_PRED)},
                    {"merge", static_cast<TPMethod> (&Map::merge)},
                    {"merge!", static_cast<TPMethod> (&Map::merge_SELF)},
                    {"key?", static_cast<TPMethod> (&Map::key_PRED)},
                    {"find", static_cast<TPMethod> (&Map::find)}
                }));
            }
            return stMethodByName;

        }

        Map::~Map() {
        }
    }
}
