
const curry = f =>
    (a, ..._) => _.length ? f(a, ..._) : (..._) => f(a, ..._);

const map = curry((f, it) => {
    const list = [];
    for (const e of it) {
        list.push(f(e));
    }
    return list;
});

const filter = curry((f, it) => {
    const list = [];
    for (const e of it) {
        if (f(e)) list.push(e);
    }
    return list;
});

const reduce = curry((f, first, iter) => {
    if (!iter) {
        iter = first[Symbol.iterator]();
        first = iter.next().value;
    }
    for (const e of iter) {
        first = f(first, e);
    }
    return first;
});

const go = (...args) => reduce((a, f) => f(a), args);
const pipe = (f, ...fs) => (...as) => go(f(...as), ...fs);


console.log('hello')