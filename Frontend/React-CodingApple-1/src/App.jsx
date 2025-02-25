import {useState} from 'react'
import './App.css'

function App() {
    let [posts, changePosts] = useState([
        { title: '총력전 공략', subtitle: '2월 17일 발행됨', likes: 0 },
        { title: '블루아카이브', subtitle: '2월 10일 발행됨', likes: 0 },
        { title: '마인크래프트 샌즈티비 좋아요', subtitle: '2월 15일 발행됨', likes: 0 }
    ]);

    let [modal, changeModal] = useState();
    let [input, changeInput] = useState('');

    function addLike(index) {
        return () => {
            posts[index].likes++;
            changePosts(posts.slice(0));
        }
    }

    return (
        <div className="App">
            <div className="black-nav">
                <h4>블로그.</h4>
            </div>
            { posts.map((post, index) =>
                <div className="list">
                    <h4 onClick={() => changeModal(post)}>{post.title}
                        <span onClick={addLike(index)}>👍</span>{post.likes}
                    </h4>
                    <p>{post.subtitle}</p>
                    <button onClick={() => {
                        let newPosts = posts.slice(0);
                        newPosts.splice(index, 1);
                        changePosts(newPosts);
                    }}>글삭제</button>
                </div>
            )}

            <input onChange={(e) => {
                console.log(e.target.value);
                changeInput(e.target.value);
            }}/>
            <button onClick={() => {
                posts.unshift({ title: input, subtitle: '2월 17일 발행됨', likes: 0 });
                changePosts(posts.slice(0));
            }}>글추가</button>
            { modal ? <Modal post={modal} onClose={() => changeModal(undefined)}/> : null}
        </div>
    )
}

function Post({post, onClick}) {
    return (
        <div className="list">
            <h4>{post.title}<span onClick={onClick}>👍</span>{post.likes}</h4>
            <p>{post.subtitle}</p>
        </div>
    )
}

function Modal({ post, onClose }) {
    return (
        <div className="modal">
            <h4>{post.title}</h4>
            <p>{post.subtitle}</p>
            <p>상세내용</p>
            <button onClick={onClose}>닫기</button>
        </div>
    )
}

export default App
