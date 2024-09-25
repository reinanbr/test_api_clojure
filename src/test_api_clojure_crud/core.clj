(ns test_api_clojure_crud.core

(:require [ring.adapter.jetty :refer [run-jetty]]
            [ring.middleware.json :refer [wrap-json-body wrap-json-response]]
            [cheshire.core :as json]
            [clojure.java.io :as io]
            [ring.util.response :refer [response not-found]]))

;; Função para salvar dados em um arquivo JSON
(defn salvar-json [dados]
  (let [arquivo "dados.json"]
    (spit arquivo (json/generate-string dados {:pretty true}) :append true)))

;; Função para imprimir os dados no console com mensagem personalizada
(defn imprimir-dados [method params]
  (if (= method :get)
    (println "Os dados GET pegos foram:" params)
    (println "Os dados POST pegos foram:" params)))

;; Handler para a rota /api/save
(defn api-save-handler [request]
  (let [method (:request-method request)
        params (if (= method :post)
                 (:body request)            ;; Obtém o corpo da requisição POST
                 (:query-params request))   ;; Obtém parâmetros da URL para GET
        response {:status 200
                  :headers {"Content-Type" "application/json"}
                  :body params}]
    
    ;; Imprimir parâmetros no console com mensagem personalizada
    (imprimir-dados method params)
    
    ;; Salvar parâmetros em um arquivo JSON
    (salvar-json params)
    
    response))

;; Handler principal para rotas
(defn handler [request]
  (case (:uri request)
    "/api/save" (api-save-handler request)  ;; Tratar somente a rota /api/save
    (not-found "Rota não encontrada")))     ;; Retorna 404 para outras rotas

;; Função principal que inicializa o servidor
(defn -main [& args]
  (let [porta 3000]
    (println (str "Servidor iniciado na porta " porta))
    (run-jetty (-> handler
                   (wrap-json-body {:keywords? true})   ;; Middleware para processar JSON no corpo do POST
                   wrap-json-response)                  ;; Middleware para enviar respostas em JSON
               {:port porta})))

