import {getAllFilms} from "./movie";

const {GoogleGenerativeAI} = require("@google/generative-ai")
const GEMINI_API_KEY = "AIzaSyCXX1U1sg89rHx1AoNePQtERN_rnHBxTNo";


const genAI = new GoogleGenerativeAI(GEMINI_API_KEY)
const model = genAI.getGenerativeModel({model: "gemini-1.5-flash"});

// const inputString = "ai là tổng thống mỹ hiện tại";

async function geminiCallApi(model: any, prompt: any) {
    const result = await model.generateContent(prompt);
    return result.response.text();
}



export async function geminiAi(inputString: any) {
    const res = await getAllFilms();
    if(!inputString){
        if(res?.code === 1000) {
            const data = res.data;
            let inputFilm = "đây là các thông tin film trong database của tôi. tôi sẽ đưa bạn câu hỏi và bạn chỉ được trả lời dựa theo thông tin ở database. Nếu câu hỏi không có thông tin ở database của tôi thì trả lời tôi không có câu trả lời cho câu hỏi này. ";
            data.map((value: any) => {
                inputFilm += "tên film " + value.name + " mô tả " + value.description;
            })
            let input = inputFilm + "đây là câu hỏi: "  + inputString;
            const messageResponse = await geminiCallApi(model, input);
            return messageResponse;
        }else {
            return "Có lỗi xảy ra khi gọi API";
        }
    }else{

    }
}
