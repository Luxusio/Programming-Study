// https://github.com/indongyoo/functional-javascript-01/blob/master/fx.js
// https://github.com/indongyoo/functional-javascript-02/blob/master/fx.js

const _ = {};
const L = {};
const C = {};

_.curry = f => (a, ...args) => args.length ? f(a, ...args) : (...args) => f(a, ...args);

_.toIter = iterable => iterable && iterable[Symbol.iterator] ? iterable[Symbol.iterator]() : empty;
_.safety = a => a != null && !!a[Symbol.iterator] ? a : empty;

_.nop = Symbol.for('nop');
_.noop = () => {};
_.rejectNop = f => e => e == _.nop ? f() : Promise.reject(e);

_.go1 = (a, f) => a instanceof Promise ? a.then(f) : f(a);

_.take = _.curry((l, iter) => {
    if (l < 1) return [];
    let res = [];
    iter = _.toIter(iter);
    return function recur() {
        let cur;
        while (!(cur = iter.next()).done) {
            const a = cur.value;
            if (a instanceof Promise) {
                return a
                    .then(a => (res.push(a), res).length == l ? res : recur())
                    .catch(_.rejectNop);
            }
            if((res.push(a), res).length == l) return res;
        }
        return res;
    }();
});
_.takeAll = _.take(Infinity);
_.head = iter => _.go1(_.take(1, iter), ([h]) => h);

L.takeWhile = _.curry(
    function *(f, iter) {
        let ok = false;
        for (const a of _.safety(iter)) {
            ok = _.go1(a, f);
            if (ok instanceof Promise) yield ok.then(_ok => (ok = _ok) ? a : Promise.reject(_.nop));
            else if (ok) yield a;
            if (!ok) break;
        }
    }
)

L.takeUntil = _.curry(
    function *(f, iter) {
        let ok = false;
        for (const a of _.safety(iter)) {
            ok = _.go1(a, f);
            if (ok instanceof Promise) yield ok.then(_ok => ((ok = _ok), a));
            else yield a;
            if (ok) break;
        }
    }
)

const reduceCall = (acc, a, f) =>
    a instanceof Promise ?
        a.then(a => f(acc, a), _.rejectNop) :
        f(acc, a);

_.reduce = (f, acc, iter) => {
    if (!acc) return (...args) => _.reduce(f, ...args);
    if (!iter) return _.reduce(f, _.head(iter = _.toIter(acc)), iter);

    iter = _.toIter(iter);
    return _.go1(acc, function recur(acc) {
        let cur;
        while(!(cur = iter.next()).done) {
            acc = reduceCall(acc, cur.value, f);
            if (acc instanceof Promise) return acc.then(recur);
        }
        return acc;
    })
}

_.call = (a, f) => f(a);

_.pipe = (f, ...fs) => (...as) => _.reduce(_.call, f(...as), fs);

_.go = (...args) => _.reduce(_.call, args);

_.each = _.curry((f, iter)=> _.go1(_.reduce((_, a) => f(a), null, iter), _ => iter));

_.pass = _.curry((f, a) => (_.go1(a, f), a));

function pushSel(parent, k, v) {
    (parent[k] || (parent[k] = [])).push(v);
    return parent;
}

_.groupBy = _.curry((f, iter) => _.reduce((group, a) => pushSel(group, f(a), a), {}, iter));



L.map = _.curry(
    function *(f, iter) {
        for(const a of _.safety(iter)) yield _.go1(a, f);
    }
)
_.map = _.curry((f, iter) => _.takeAll(L.map(f, iter)));

L.filter = _.curry(
    function *(f, iter) {
        for(const a of _.safety(iter)) {
            const b = _.go1(a, f);
            if (b instanceof Promise) yield b.then(b => b ? a : Promise.reject(_.nop));
            else if (b) yield a;
        }
    }
)
_.filter = _.curry((f, iter) => _.takeAll(L.filter(f, iter)));

L.reject = _.curry(
    function *(f, iter) {
        for(const a of _.safety(iter)) {
            const b = _.go1(a, f);
            if (b instanceof Promise) yield b.then(b => b ? Promise.reject(_.nop) : a);
            else if (!b) yield a;
        }
    }
)
_.reject = _.curry((f, iter) => _.takeAll(L.reject(f, iter)));

L.range = function *(begin = 0, end = begin, step = 1) {
    if (end == begin) begin = 0;
    while (begin < end) {
        yield begin;
        begin += step;
    }
}
_.range = (...args) => _.takeAll(L.range(...args));

L.keys = function *(obj) {
    for (const k in obj) yield k;
}
_.keys = obj => _.takeAll(L.keys(obj));

L.values = function *(obj) {
    for (const k in obj) yield obj[k];
}
_.values = obj => _.takeAll(L.values(obj));

L.entries = function *(obj) {
    for (const k in obj) yiekd [k, obj[k]];
}
_.entries = obj => _.takeAll(L.entries(obj));



// concurrent
const catchNoop = ([...arr]) => (arr.forEach(a => a instanceof Promise ? a.catch(_.noop) : a), arr);

C.reduce = _.curry((f, acc, iter) => iter ?
    _.reduce(f, acc, catchNoop(iter)) :
    _.reduce(f, catchNoop(iter)));

C.take = _.curry((l, iter) => _.take(l, catchNoop(iter)));
C.takeAll = C.take(Infinity);
C.map = _.curry(_.pipe(L.map, C.takeAll));
C.filter = _.curry(_.pipe(L.filter, C.takeAll));

