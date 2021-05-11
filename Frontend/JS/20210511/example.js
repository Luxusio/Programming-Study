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


const products = [
    {name: '반팔티', price: 15000, quantity: 1, is_selected: true},
    {name: '긴팔티', price: 20000, quantity: 2, is_selected: false},
    {name: '핸드폰케이스', price: 15000, quantity: 3, is_selected: true},
    {name: '후드티', price: 30000, quantity: 4, is_selected: false},
    {name: '바지', price: 25000, quantity: 5, is_selected: false}
  ];

const add = (a, b) => a + b;

const sum = (f, iter) => go(
    iter,
    map(f),
    reduce(add),
);

console.log(sum(e=>e.price * e.quantity, products));
console.log(sum(e=>e.quantity, products));

