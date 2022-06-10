import React, {useState, useReducer} from 'react'

const ACTIONS = {INCREMENT: 'increment', DECREMENT: 'decrement'}

//          current state, action
function reducer(state, action){
    switch (action.type) {
        case ACTIONS.INCREMENT: return {count: state.count +1}
        case ACTIONS.DECREMENT: return {count: state.count -1};
        default: return state;
    }
}

const NEWACTIONS = {ADD_TODO: "addTodo"}
function todosReducer(todos, action){
    switch (action.type) {
        case NEWACTIONS.ADD_TODO: return [...todos, newTodo(action.payload.name)]
        default: return todos;
    }
}

function todosReducerDestructurd(todos, {type, payload}){
    switch (type) {
        case NEWACTIONS.ADD_TODO: return [...todos, newTodo(payload.name)]
        default: return todos;
    }
}



function newTodo(name){
    return {id: Date.now(), name: name, complete: false};
}
export default function ReducerExample(){
    //current state   update method
    const [state, dispatch] = useReducer(reducer, {count: 0});
    const [count, setCount] = useState(0);

    const [todos, dispatchTodos] = useReducer(todosReducer, []);
    const [name, setName] = useState("")


    function increment(){
        dispatch({type: ACTIONS.INCREMENT});
        // setCount(prevState => prevState +1);
    }

    function decrement(){
        dispatch({type: ACTIONS.DECREMENT})
       // setCount(prevState => prevState -1);
    }

    function handleSubmit(e){
        e.preventDefault();
        dispatch({type: NEWACTIONS.ADD_TODO, payload: {name: name}})
        setName("");
    }

    return (
        <>
            <button onClick={decrement}>-</button>
            <span>{state.count}</span>
            <button onClick={increment}>+</button>

            <form onSubmit={handleSubmit}>
                <input type="text" value={name} onChange={e=> setName(e.target.value)}/>
            </form>
        </>
    )
}

//REDUX STORE


/**
 * main.js --> Provide store
 *
 * import store from './app/store'
 * import { Provider } from 'react-redux'
 *
 * ReactDOM.render(
 *   <Provider store={store}>
 *     <App />
 *   </Provider>,
 *   document.getElementById('root')
 * )
 *
 *
 * app/store.js --> ConfigureStore
 *
 *
 *
 * import { configureStore } from '@reduxjs/toolkit'
 * import todosReducer from '../reduxStore/todos/todosSlice'
 * import filtersReducer from '../reduxStore/filters/filtersSlice'
 *
 * export const store = configureStore({
 *   reducer: {
 *     todos: todosReducer,
 *     filters: filtersReducer              <-- This is a completly new slice>
 *   }
 * })
 *
 *
 * myExample
 * export const store = configureStore({
 *   reducer: {
 *     connection: connectionsReducer,
 *     drawboardElement: drawboardElementsReducer              <-- This is a completly new slice>
 *   }
 * })
 */


/**
 * --> Create modification actions
 * todoSlice.js
 *
 * import { createSlice } from '@reduxjs/toolkit'
 *
 * const todosSlice = createSlice({
 *   name: 'todos',
 *   initialState: [],
 *   reducers: {
 *     todoAdded(state, action) {
 *       state.push({
 *         id: action.payload.id,
 *         text: action.payload.text,
 *         completed: false
 *       })
 *     },
 *     todoToggled(state, action) {
 *       const todo = state.find(todo => todo.id === action.payload)
 *       todo.completed = !todo.completed
 *     }
 *   }
 * })
 *
 * export const { todoAdded, todoToggled } = todosSlice.actions
 * export default todosSlice.reducer
 */


/**
 * Use in all react components inside provider
 *
 * import { decrement, increment } from './counterSlice'
 *
 *   const count = useSelector(state => state.counter.value)
 *   const dispatch = useDispatch()
 *
 * --> dispatch(increment)
 * --> dispatch(decrement)
 */


/**
 * Middlware with api
 * https://redux.js.org/tutorials/essentials/part-5-async-logic
 *
 * Thunk Functions as Middleware
 *
 *
 *
 * export function saveNewTodo(text) {
 *
 *   return async function saveNewTodoThunk(dispatch, getState) {
 *
 *     const initialTodo = { text }
 *     const response = await client.post('/fakeApi/todos', { todo: initialTodo })
 *     dispatch({ type: 'todos/todoAdded', payload: response.todo })
 *   }
 * }
 *
 *
 *
 * import React, { useState } from 'react'
 * import { useDispatch } from 'react-redux'
 *
 * import { saveNewTodo } from '../todos/todosSlice'
 *
 * const Header = () => {
 *   const [text, setText] = useState('')
 *   const dispatch = useDispatch()
 *
 *   const handleChange = e => setText(e.target.value)
 *
 *   const handleKeyDown = e => {
 *     // If the user pressed the Enter key:
 *     const trimmedText = text.trim()
 *     if (e.which === 13 && trimmedText) {
 *
 *
 *       // Create the thunk function with the text the user wrote
 *       const saveNewTodoThunk = saveNewTodo(trimmedText)
 *       // Then dispatch the thunk function itself
 *       dispatch(saveNewTodoThunk)
 *
 *
 *       oder direkt
 *           // Create the thunk function and immediately dispatch it
 *     dispatch(saveNewTodo(trimmedText))
 *
 *       setText('')
 *     }
 *   }
 *
 *   // omit rendering output
 * }
 */