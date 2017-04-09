# silverbar-orderboard

Comments/assumptions:
1. The data storage structure was chosen to be as simple as possible while allowing fast inserts/removals. Any ordering/sorting is performed on retrieval.
2. It was assumed registering orders of the same user, quantity, price and order type would overwrite a previous one to keep it simple (it could be easily extended by appending timestamp to the key).
