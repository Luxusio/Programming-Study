// https://github.com/indongyoo/functional-javascript-01/blob/master/fx.js
// https://github.com/indongyoo/functional-javascript-02/blob/master/fx.js

const _ = {};
const L = {};
const C = {};

const nop = Symbol.for('nop');
const noop = () => {};

_.nopReject = (f, e) => e == nop ? f() : Promise.reject(e);

_.toIter = iterable => iterable && iterable[Symbol.iterator] ? iterable[Symbol.iterator]() : empty;
_.safety = a => a != null && !!a[Symbol.iterator] ? a : empty;

_.curry = f => (a, ..._)=> _.length ? f(a, ..._) : (..._) => f(a, ..._);
_.go1 = (a, f) => a instanceof Promise ? a.then(f) : f(a);

L.take = _.curry(function *lTake(l, iter) {
    if (l < 1) return;
    for (const a of safety(iter)) {
        if (a instanceof Promise) yield a.then(a => (--l, a));
        else yield (--l, a);
        if (!l) break;
    }
});

_.take = _.curry(
    (l, iter)=> {
        let res = [];
        iter = _.toIter(iter);
        return function recur() {
            let cur;
            while (!(cur = iter.next).done) {
                const a = cur.value;
                if (a instanceof Promise) {
                    return a
                        .then(a => (res.push(a), res).length == l ? res : recur())
                        .catch(_.nopReject(recur));
                }
                if((res.push(a), res).length == l) return res;
            }
            return res;
        } ();
    }
);

_.takeAll = (iter => _.take(Infinity, iter));
_.head = iter => _.go1(_.take(1, iter), ([h]) => h);




const reduceF = (acc, a, f) =>
    a instanceof Promise ?
        a.then(a => f(acc, a), _.nopReject(acc)) :
        f(acc, a);

_.reduce = _.curry((f, acc, iter) => {
    if (!acc) return (..._) => _.reduce(f, ..._);
    if (!iter) return _.reduce(f, _.head(iter = _.toIter(acc)), iter);
    
    iter = iter[Symbol.iterator]();
    return _.go1(acc, function recur(acc) {
        let cur;
        while (!(cur = iter.next()).done) {
            acc = reduceF(acc, cur.value, f);
            if (acc instanceof Promise) return acc.then(recur);
        }
        return acc;
    });
});

_.each = _.curry((f, iter) => _.go1(_.reduce((_, a) => f(a), null, iter), _ => iter));

_.go = (...args) => _.reduce(call, args);
_.pipe = (f, ...fs) => (...as) => _.go(f(...as), ...fs);




L.range = function* lRange(begin = 0, end = begin, step = 1) {
    if (end == begin) begin = 0;
    while (begin < end) {
        yield begin;
        begin += step;
    }
}
_.range = (...args) => _.takeAll(L.range(...args));

L.map = _.curry(
    function *lMap(f, iter) {
        for (const a of safety(iter)) {
            yield _.go1(a, f);
        }
    }
);
_.map = _.curry((f, iter) => _.takeAll(L.map(f, iter)));

L.filter = _.curry(
    function *lFilter(f, iter) {
        for (const a of safety(iter)) {
            const b = _.go1(a, f);
            if (b instanceof Promise) yield b.then(b => b ? a : Promise.reject(nop));
            else if (b) yield a;
        }
    }
);
_.filter = _.curry((f, iter) => _.takeAll(L.filter(f, iter)));


// concurrent
const catchNoop = ([...arr]) => (arr.forEach(a => a instanceof Promise ? a.catch(noop) : a), arr);

C.reduce = _.curry((f, acc, iter) => iter ?
    _.reduce(f, acc, catchNoop(iter)) :
    _.reduce(f, catchNoop(iter)));

C.take = _.curry((l, iter) => take(l, catchNoop(iter)));
C.takeAll = C.take(Infinity);
C.map = _.curry(_.pipe(L.map, C.takeAll));
C.filter = _.curry(_.pipe(L.filter, C.takeAll));

console.log('Fx.js end', _.takeAll);